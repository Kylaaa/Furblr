package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.IPostTag
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.networking.requests.RequestSubmissions
import com.itreallyiskyler.furblr.networking.requests.RequestView
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.furblr.ui.home.HomePagePost
import okhttp3.internal.toImmutableList

object ContentManager {
    // DATABASE CONNECTIONS
    private lateinit var db : AppDatabase
    fun setDB(appDB : AppDatabase) { db = appDB }

    // HOME PAGE CONTENT
    private var homePagePostIds : MutableList<Long> = mutableListOf()
    private var homePagePosts : MutableList<HomePagePost> = mutableListOf()
    val HomePageContentReady = Signal<List<HomePagePost>>()
    private fun getHomePagePosts() : List<HomePagePost> { return homePagePosts.toImmutableList() }

    // LOADING FUNCTIONS
    private fun clobberHomePageContent(){
        // TODO : figure out how to properly page this nonsense
        homePagePostIds.clear()
        val homeDao = db.homePageDao()
        val blacklistedTags = homeDao.getBlacklistedTags().map { tag : BlacklistedTag -> tag.tagContents }.toSet()

        val posts = homeDao.getHomePagePostsByPage(48, 0)
        val postIds = posts.map { post -> post.id }
        val tags = homeDao.getTagsForPosts(postIds)
        val comments = homeDao.getCommentsForPosts(postIds)

        // clobber together the data
        posts.forEach { post -> run {
            val postTags = tags.filter { tag -> tag.parentPostId == post.id }
            val postComments = comments.filter { comment -> comment.postId == post.id }
            val hpp = HomePagePost(post, postTags, postComments)

            // filter out any blacklisted content
            var isBlacklisted = false
            postTags.forEach { tag ->
                isBlacklisted = isBlacklisted || blacklistedTags.contains(tag.tagContents)
            }
            if (!isBlacklisted) {
                homePagePosts.add(hpp)
            }
        }}

        HomePageContentReady.fire(getHomePagePosts())
    }

    fun fetchSubmissions() {
        RequestSubmissions().fetchContent()
            .then(fun(pageSubmissions : Any) : Set<Long> {
                // collect a list of the most recent postIds
                val submissions = pageSubmissions as PageSubmissions
                submissions.Submissions.forEach { submission -> homePagePostIds.add(submission.postId) }

                // check if we have pulled down that content yet
                val missingIds = homePagePostIds.toMutableSet()
                val existingPosts = db.postsDao().getExistingPostsWithIds(homePagePostIds.toImmutableList())
                existingPosts.forEach{ post -> missingIds.remove(post.id) }

                return missingIds.toSet()
            }, fun(submissionsFetchFailureDetails : Any) : Set<Long> {
                // TODO : Signal that the original fetch failed
                println(submissionsFetchFailureDetails)
                return emptySet<Long>()
            })
            .then(fun(setOfMissingIds : Any) : Promise {
                val missingIds = setOfMissingIds as Set<Long>
                // pull down details for each of the missing posts
                val fetchPromises : MutableList<Promise> = mutableListOf()
                missingIds.forEach { postId: Long ->
                    run {
                        // fetch the details for each id that we are missing
                        val parsePromise = RequestView(postId).fetchContent().then(fun(details : Any) {
                            storePagePostDetails(postId, details as PagePostDetails)
                        }, fun(errorDetails : Any) {
                            // TODO : signal that a page failed to load somehow
                            println("$postId failed to load : $errorDetails")
                        })
                        fetchPromises.add(parsePromise)
                    } // end run-block
                } // end for-each

                return Promise.all(fetchPromises.toTypedArray())
            }, fun(missingPostsFetchFailureDetails : Any) {
                // TODO : handle the error
                println("Fetching the missing posts threw an error : $missingPostsFetchFailureDetails")
            })
            .then(fun(_ : Any){
                clobberHomePageContent()
            }, null)
    }
    private fun storePagePostDetails(postId : Long, pagePostDetails : PagePostDetails) {
        // Add the post to the Posts table
        val post = Post(
            postId,
            pagePostDetails.Artist,
            pagePostDetails.Title,
            pagePostDetails.Description,
            postId.toString(),
            pagePostDetails.TotalViews,
            pagePostDetails.Comments.count().toLong(),
            pagePostDetails.TotalFavorites,
            pagePostDetails.Rating.toString(),
            pagePostDetails.UploadDate
        )
        db.postsDao().insertOrUpdate(post)

        // add each comment to the Comments table
        pagePostDetails.Comments.forEach { comment: IPostComment ->
            run {
                val commentEntity = Comment(
                    comment.Id,
                    postId,
                    comment.UploaderName,
                    comment.Content,
                    comment.Date
                )
                db.commentsDao().insertOrUpdateComment(commentEntity)
            }
        }

        // Add each tag to the Tags table
        pagePostDetails.Tags.forEach { tag: IPostTag ->
            run {
                val tagEntity = Tag(postId, tag.Content)
                db.tagsDao().insertOrUpdateTag(tagEntity)
            }
        }
    }
}