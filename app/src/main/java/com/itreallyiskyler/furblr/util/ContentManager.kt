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
import com.itreallyiskyler.furblr.util.thunks.FetchHomePageContent
import okhttp3.internal.toImmutableList
import kotlin.concurrent.thread

object ContentManager {
    // DATABASE CONNECTIONS
    private lateinit var db : AppDatabase
    fun setDB(appDB : AppDatabase) { db = appDB }

    // HOME PAGE CONTENT
    var homePagePosts : List<HomePagePost> = listOf()
    val HomePageContentReady = Signal<List<HomePagePost>>()

    // LOADING FUNCTIONS
    fun fetchSubmissions(page : Int = 0,
                         pageSize : Int = 48,
                         forceReload : Boolean = false) {
        thread(start = true, name="FetchHomePageSubmissionsThread") {
            var fetchPromise = FetchHomePageContent(db, page, pageSize, forceReload)
            fetchPromise
                .then(fun(posts: Any) {
                    // TODO : Figure out why this is returning a promise and not a list of posts
                    homePagePosts = posts as List<HomePagePost>
                    HomePageContentReady.fire(homePagePosts)
                }, fun(errMessage: Any) {
                    println(errMessage)
                    HomePageContentReady.fire(homePagePosts)
                })
        }
    }
}