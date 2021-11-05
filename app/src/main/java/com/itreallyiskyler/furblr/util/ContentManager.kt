package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.furblr.ui.home.HomePagePost
import com.itreallyiskyler.furblr.util.thunks.FetchHomePageContent
import kotlin.concurrent.thread

object ContentManager {
    // DATABASE CONNECTIONS
    private lateinit var db : AppDatabase
    fun setDB(appDB : AppDatabase) { db = appDB }

    // HOME PAGE CONTENT
    var homePagePosts : List<HomePagePost> = listOf()
    val HomePageContentReady = Signal<List<HomePagePost>>()
    val HomePageContentUpdated = Signal<HomePagePost>()

    // indexing helpers
    var homePageIndices : HashMap<Long, Int> = hashMapOf()
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
        thread(start = true, name="FetchHomePageSubmissionsThread") {
            var fetchPromise = FetchHomePageContent(db, page, pageSize, forceReload)
            fetchPromise
                .then(fun(posts: Any?) {
                    homePagePosts = posts as List<HomePagePost>
                    indexHomePagePosts(homePagePosts)
                    HomePageContentReady.fire(homePagePosts)
                }, fun(errMessage: Any?) {
                    println(errMessage)
                    HomePageContentReady.fire(homePagePosts)
                })
        }
    }
    fun updateHomePagePost(post : HomePagePost) {
        val postId = post.postData.id
        if (!homePageIndices.containsKey(postId))
        {
            throw IndexOutOfBoundsException("Could not find $postId in homePageIndices")
        }




    }
}