package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.networking.requests.RequestFavoritePost
import com.itreallyiskyler.furblr.networking.requests.RequestUnfavoritePost
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.discover.DiscoverViewModel
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.HomeViewModel
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.ui.notifications.NotificationsViewModel
import com.itreallyiskyler.furblr.util.thunks.*
import kotlin.concurrent.thread

object ContentManager {
    private lateinit var db : AppDatabase
    fun setDB(appDB : AppDatabase) { db = appDB }

    var discoverVM : DiscoverViewModel = DiscoverViewModel()
    var homeVM : HomeViewModel = HomeViewModel()
    var notesVM : NotificationsViewModel = NotificationsViewModel()


    fun fetchStartupData() {
        thread(start = true, name = "StartUpFetchThread") {
            val promises = arrayOf(
                fetchNotifications(false),
                fetchSubmissions(0, 48, false))

            Promise.all(promises).then(fun(_ : Any?) {
                val contentIds = db.contentFeedDao().getPageFromFeed(ContentFeedId.Home.id, 48, 0)
                val postIds = contentIds.filter { it.postKind == PostKind.Image.id }.map { it.postId }
                val journalIds = contentIds.filter { it.postKind == PostKind.Text.id }.map { it.postId }

                val posts = ClobberHomePageImagesById(db, postIds)
                val journals = ClobberHomePageTextsById(db, journalIds)
                val homeContent = (posts + journals).sortedByDescending { it.postDate }.toMutableList()
                homeVM.setPosts(homeContent)

                val notes = ClobberNotificationsByPage(db)
                notesVM.setNotifications(notes)

                val unreadCount : Int = db.notificationsDao().getUnreadNotificationCount()
                notesVM.updateUnreadNotifications(unreadCount)
            }, fun(failureReason : Any?) {
                homeVM.setPosts(listOf())
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
        return FetchPageOfHome(db, page, pageSize, forceReload)
    }

    fun fetchLatestHomePagePost(post : IHomePageContent) {
        val postId = post.contentId
        if (post.postKind == PostKind.Image) {
            // re-fetch the data from the web
            thread(start = true, name = "UpdateHomePageSubmissionThread") {
                FetchLatestForPost(db, postId, ContentFeedId.Home)
                    .then(fun(updatedPost: Any?) {
                        val updatedHomePagePost = (updatedPost as List<HomePageImagePost>)[0]
                        homeVM.updatePost(postId, updatedHomePagePost)
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

    fun markNotificationsAsRead() {
        db.notificationsDao().markNotificationAsSeen()
        notesVM.updateUnreadNotifications(0)
    }

    fun fetchSearchPage(keyword : String, options : SearchOptions) : Promise {
        return FetchPageOfSearch(db, keyword, options)
    }
}