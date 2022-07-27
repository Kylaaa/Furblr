package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.networking.requests.RequestFavoritePost
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.networking.requests.RequestUnfavoritePost
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.discover.DiscoverViewModel
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.HomeViewModel
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.ui.notifications.NotificationsViewModel
import com.itreallyiskyler.furblr.ui.search.SearchViewModel
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import com.itreallyiskyler.furblr.util.thunks.*
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

object ContentManager {
    private var didFetchStartupData : Boolean = false
    private val db : AppDatabase = DBManager.getDB()
    private val requestHandler : RequestHandler = NetworkingManager.requestHandler
    private val loggingChannel : LoggingChannel = LoggingManager.createChannel("Content Manager", LogLevel.ERROR)

    var discoverVM : DiscoverViewModel = DiscoverViewModel()
    var homeVM : HomeViewModel = HomeViewModel()
    var notesVM : NotificationsViewModel = NotificationsViewModel()
    var searchVM : SearchViewModel = SearchViewModel()


    fun fetchStartupData() {
        // debounce multiple requests
        if (didFetchStartupData) {
            return
        }
        didFetchStartupData = true

        thread(start = true, name = "StartUpFetchThread") {
            // quickly load data that we've already fetched
            val timeMS = measureTimeMillis {
                loadPageOfHomeFromDB(0)
            }
            loggingChannel.logInfo("Populated default data in $timeMS ms")

            val promises = arrayOf(
                fetchNotifications(false), // fetches Journals and Notifications
                fetchSubmissions(0, 48, false) // fetches Submissions Feed
            )

            Promise.all(promises).then(fun(_ : Any?) {
                loadPageOfHomeFromDB(0)
            }, fun(failureReason : Any?) {
                homeVM.setPosts(listOf())
                loggingChannel.logError("Startup fetched failed with : $failureReason")
            })
        }
        thread(start = true, name = "NonEssentialStartUpFetchThread") {
            val promises = arrayOf(
                fetchDiscovery()
            )
            Promise.all(promises).catch(fun(failureReason : Any?) {
                loggingChannel.logError("Nonessentials failed to fetch with error : $failureReason")
            })
        }
    }
    private fun getDiscoveryViewsOfKind(kind: PostKind) : MutableList<HomePageImagePost> {
        val contentIds = db.contentFeedDao().getPageFromFeedOfKind(listOf(ContentFeedId.Discover.id), kind.id, 20, 0)
        val viewIds = contentIds.map { it.postId }
        val views = ClobberHomePageImagesById(db, viewIds)
        return views.toMutableList()
    }

    // LOADING FUNCTIONS
    fun loadPageOfHomeFromDB(page : Int = 0) {
        val contentIds = db.contentFeedDao().getPageFromFeed(listOf(ContentFeedId.Home.id), 48, page)
        // filter the ids based on what db table to read the data from
        val postIds = contentIds.filter { it.postKind != PostKind.Journal.id }.map { it.postId }
        val journalIds = contentIds.filter { it.postKind == PostKind.Journal.id }.map { it.postId }

        val posts = ClobberHomePageImagesById(db, postIds)
        val journals = ClobberHomePageTextsById(db, journalIds)
        val homeContent = (posts + journals).sortedByDescending { it.postDate }.toMutableList()
        homeVM.setPosts(homeContent)

        val notes = ClobberNotificationsByPage(db)
        notesVM.setNotifications(notes)

        val unreadCount : Int = db.notificationsDao().getUnreadNotificationCount()
        notesVM.updateUnreadNotifications(unreadCount)
    }
    fun fetchNotifications(forceReload: Boolean = false) : Promise {
        return FetchNotifications(db, requestHandler, forceReload)
    }

    fun fetchSubmissions(page : Int = 0,
                         pageSize : Int = 48,
                         forceReload : Boolean = false) : Promise {
        return FetchPageOfHome(db, page, pageSize, forceReload)
    }

    fun fetchDiscovery() : Promise {
        return FetchPageOfDiscovery(db, false).then(fun(_ : Any?) {
            val images = getDiscoveryViewsOfKind(PostKind.Image)
            discoverVM.setNewSubmissionsData(images)

            val writings = getDiscoveryViewsOfKind(PostKind.Writing)
            discoverVM.setNewWritingData(writings)

            val musics = getDiscoveryViewsOfKind(PostKind.Music)
            discoverVM.setNewMusicData(musics)

        }, fun(failureReason : Any?) {
            loggingChannel.logError("Failed to fetch and set Discovery content with error : $failureReason")
        })
    }

    fun fetchLatestHomePagePost(post : IHomePageContent) {
        val postId = post.contentId
        if (post.postKind != PostKind.Journal) {
            // re-fetch the data from the web
            FetchLatestForPost(db, postId, ContentFeedId.Home)
                .then(fun(updatedPost: Any?) {
                    val updatedHomePagePost = (updatedPost as List<HomePageImagePost>)[0]
                    homeVM.updatePost(postId, updatedHomePagePost)
                }, fun(errData: Any?) {
                    loggingChannel.logError("Failed to fetch latest for view/$postId with error : $errData")
                })
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

        return request.fetchContent()
            .then(fun(_ : Any?) {
                fetchLatestHomePagePost(imagePost)
            }, fun(errDetails : Any?){
                loggingChannel.logError(errDetails)
            })
    }

    fun markNotificationsAsRead() {
        db.notificationsDao().markNotificationAsSeen()
        notesVM.updateUnreadNotifications(0)
    }

    fun fetchSearchPage(keyword : String, options : SearchOptions) : Promise {
        return FetchPageOfSearch(db, keyword, options).then(fun(listOfPosts : Any?) {
            searchVM.setSearchQuery(keyword)
            searchVM.setSearchParameters(options)
            searchVM.setSearchResults(listOfPosts as List<HomePageImagePost>)
            //searchVM.SearchResultsReady.fire(listOfPosts as List<HomePageImagePost>)
        }, fun(failureToFetchDetails : Any?) {
            loggingChannel.logError("Failed to fetch page of search with reason : $failureToFetchDetails")
        })
    }
}