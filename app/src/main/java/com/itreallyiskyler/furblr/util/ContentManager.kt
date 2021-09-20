package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.IPostTag
import com.itreallyiskyler.furblr.networking.requests.RequestSubmissions
import com.itreallyiskyler.furblr.networking.requests.RequestView
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.furblr.ui.home.HomePagePost
import okhttp3.internal.toImmutableList

object ContentManager {
    // DATABASE CONNECTIONS
    private lateinit var db : AppDatabase;
    fun setDB(appDB : AppDatabase) { db = appDB; }

    // HOME PAGE CONTENT
    private var homePagePostIds : MutableList<Long> = mutableListOf();
    private var homePagePosts : MutableList<HomePagePost> = mutableListOf();
    public val HomePageContentReady = Signal<List<HomePagePost>>();
    fun getHomePagePosts() : List<HomePagePost> { return homePagePosts.toImmutableList() }

    // LOADING FUNCTIONS
    private var totalPostsToLoad = 0;
    private fun markPostLoaded() {
        totalPostsToLoad = totalPostsToLoad - 1
        if (totalPostsToLoad <= 0) {
            clobberHomePageContent()
        }
    }
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
            var postTags = tags.filter { tag -> tag.parentPostId == post.id }
            var postComments = comments.filter { comment -> comment.postId == post.id }
            var hpp = HomePagePost(post, postTags, postComments)

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
        RequestSubmissions().getContent { pageSubmissions ->
            // collect a list of the most recent postIds
            pageSubmissions.Submissions.forEach { submission -> homePagePostIds.add(submission.postId) }
            println(homePagePostIds)

            // check if we have pulled down that content yet
            var missingIds = homePagePostIds.toMutableSet()
            var existingPosts = db.postsDao().getExistingPostsWithIds(homePagePostIds.toImmutableList())
            println(existingPosts)
            existingPosts.forEach{ post -> missingIds.remove(post.id) }

            // pull down details for each of the missing posts
            totalPostsToLoad = missingIds.size
            if (totalPostsToLoad == 0) {
                clobberHomePageContent()
            }
            else {
                missingIds.forEach { postId: Long ->
                    RequestView(postId).getContent { pagePostDetails ->
                        run {
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

                            // decrement the counter for total items left to load...
                            markPostLoaded()
                        }

                    }
                }
            }
        };
    }
}