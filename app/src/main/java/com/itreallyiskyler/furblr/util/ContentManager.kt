package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.networking.requests.RequestFavoritePost
import com.itreallyiskyler.furblr.networking.requests.RequestUnfavoritePost
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.home.HomePagePost
import com.itreallyiskyler.furblr.util.thunks.FetchHomePageContent
import com.itreallyiskyler.furblr.util.thunks.FetchLatestForPost
import kotlin.concurrent.thread

object ContentManager {
    // DATABASE CONNECTIONS
    private lateinit var db : AppDatabase
    fun setDB(appDB : AppDatabase) { db = appDB }

    // HOME PAGE CONTENT
    var homePagePosts : MutableList<HomePagePost> = mutableListOf()
    val HomePageContentReady = Signal<List<HomePagePost>>()
    val HomePageContentUpdated = Signal2<Int, HomePagePost>()

    // indexing helpers
    private var homePageIndices : HashMap<Long, Int> = hashMapOf()
    private fun indexHomePagePosts(posts : List<HomePagePost>){
        for (i in posts.indices) {
            val post = posts[i]
            homePageIndices[post.postData.id] = i
        }
    }

    // LOADING FUNCTIONS
    fun fetchSubmissions(page : Int = 0,
                         pageSize : Int = 48,
                         forceReload : Boolean = false) {
        thread(start = true, name = "FetchHomePageSubmissionsThread") {
            var fetchPromise = FetchHomePageContent(db, page, pageSize, forceReload)
            fetchPromise
                .then(fun(posts: Any?) {
                    homePagePosts = (posts as List<HomePagePost>).toMutableList()
                    indexHomePagePosts(homePagePosts)
                    HomePageContentReady.fire(homePagePosts.toList())
                }, fun(errMessage: Any?) {
                    println(errMessage)
                    HomePageContentReady.fire(homePagePosts.toList())
                })
        }
    }

    fun fetchLatestHomePagePost(post : HomePagePost) {
        val postId = post.postData.id
        if (!homePageIndices.containsKey(postId))
        {
            throw IndexOutOfBoundsException("Could not find $postId in homePageIndices")
        }

        // refetch the data from the web
        thread(start = true, name = "UpdateHomePageSubmissionThread") {
            FetchLatestForPost(db, postId)
                .then(fun(updatedPost : Any?) {
                    val updatedHomePagePost = (updatedPost as List<HomePagePost>)[0]
                    val index: Int = homePageIndices[postId]!!
                    homePagePosts[index] = updatedHomePagePost

                    // alert the listeners that the data has been updated
                    HomePageContentUpdated.fire(index, homePagePosts[index])
                }, fun(errData : Any?) {
                    println(errData)
                })
        }
    }

    fun favoritePost(post : HomePagePost) : Promise {
        val id = post.postData.id
        val key = post.postData.favKey
        val isFavoritedAlready = post.postData.hasFavorited

        val request : IRequestAction = if (isFavoritedAlready)
            RequestUnfavoritePost(id, key)
        else
            RequestFavoritePost(id, key)

        return request.performAction()
            .then(fun(_ : Any?) {
                fetchLatestHomePagePost(post)
            }, fun(errDetails : Any?){
                println(errDetails)
            })
    }
}