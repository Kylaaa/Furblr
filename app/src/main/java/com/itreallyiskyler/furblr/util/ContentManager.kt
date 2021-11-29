package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.networking.requests.RequestFavoritePost
import com.itreallyiskyler.furblr.networking.requests.RequestUnfavoritePost
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.thunks.*
import kotlin.concurrent.thread

object ContentManager {
    // DATABASE CONNECTIONS
    private lateinit var db : AppDatabase
    fun setDB(appDB : AppDatabase) { db = appDB }

    // HOME PAGE CONTENT
    var homePagePosts : MutableList<IHomePageContent> = mutableListOf()
    val HomePageContentReady = Signal<List<IHomePageContent>>()
    val HomePageContentUpdated = Signal2<Int, IHomePageContent>()

    // indexing helpers
    private var homePageIndices : HashMap<Long, Int> = hashMapOf()
    private fun indexHomePagePosts(imagePosts : List<IHomePageContent>){
        for (i in imagePosts.indices) {
            val post = imagePosts[i]
            homePageIndices[post.contentId] = i
        }
    }

    fun fetchStartupData() {
        thread(start = true, name = "StartUpFetchThread") {
            val promises = arrayOf(fetchNotifications(false), fetchSubmissions(0, 48, false))
            Promise.all(promises).then(fun(_ : Any?) {
                val contentIds = db.contentFeedDao().getPageFromFeed(ContentFeedId.Home.id, 48, 0)
                //val contentIds = db.contentFeedDao().getAllFeedIds()
                val postIds = contentIds.filter { it.postKind == PostKind.Image.id }.map { it.postId }
                val journalIds = contentIds.filter { it.postKind == PostKind.Text.id }.map { it.postId }

                val posts = ClobberHomePageImagesById(db, postIds)
                val journals = ClobberHomePageTextsById(db, journalIds)

                homePagePosts = (posts + journals).sortedByDescending { it.postDate }.toMutableList()
                indexHomePagePosts(homePagePosts)
                HomePageContentReady.fire(homePagePosts.toList())
            }, fun(failureReason : Any?) {
                HomePageContentReady.fire(homePagePosts.toList())
                println(failureReason)
            })
        }
    }

    // LOADING FUNCTIONS
    fun fetchNotifications(forceReload: Boolean = false) : Promise {
        return FetchNotifications(db, forceReload)
    }

    fun fetchSubmissions(page : Int = 0,
                         pageSize : Int = 48,
                         forceReload : Boolean = false) : Promise {
       return FetchHomePageContent(db, page, pageSize, forceReload)
    }

    fun fetchLatestHomePagePost(post : IHomePageContent) {
        val postId = post.contentId
        if (!homePageIndices.containsKey(postId)) {
            throw IndexOutOfBoundsException("Could not find $postId in homePageIndices")
        }

        if (post.postKind == PostKind.Image) {
            // refetch the data from the web
            thread(start = true, name = "UpdateHomePageSubmissionThread") {
                FetchLatestForPost(db, postId, ContentFeedId.Home)
                    .then(fun(updatedPost: Any?) {
                        val updatedHomePagePost = (updatedPost as List<HomePageImagePost>)[0]
                        val index: Int = homePageIndices[postId]!!
                        homePagePosts[index] = updatedHomePagePost

                        // alert the listeners that the data has been updated
                        HomePageContentUpdated.fire(index, homePagePosts[index])
                    }, fun(errData: Any?) {
                        println(errData)
                    })
            }
        }
        else if (post.postKind == PostKind.Text) {

        }
    }

    fun favoritePost(imagePost : HomePageImagePost) : Promise {
        val id = imagePost.postData.id
        val key = imagePost.postData.favKey
        val isFavoritedAlready = imagePost.postData.hasFavorited

        val request : IRequestAction = if (isFavoritedAlready)
            RequestUnfavoritePost(id, key)
        else
            RequestFavoritePost(id, key)

        return request.performAction()
            .then(fun(_ : Any?) {
                fetchLatestHomePagePost(imagePost)
            }, fun(errDetails : Any?){
                println(errDetails)
            })
    }
}