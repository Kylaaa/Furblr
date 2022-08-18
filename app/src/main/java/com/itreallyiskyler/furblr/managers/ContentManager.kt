package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.ContentFeedId
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
import com.itreallyiskyler.furblr.util.Profiler
import com.itreallyiskyler.furblr.util.Promise
import com.itreallyiskyler.furblr.util.thunks.*
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class ContentManager(
    private val db : AppDatabase,
    private val requestHandler : RequestHandler,
    private val loggingChannel : LoggingChannel
) : IContentManager {
    private var didFetchStartupData : Boolean = false

    override val discoverVM : DiscoverViewModel = DiscoverViewModel()
    override val homeVM : HomeViewModel = HomeViewModel()
    override val notesVM : NotificationsViewModel = NotificationsViewModel()
    override val searchVM : SearchViewModel = SearchViewModel()

    override fun fetchStartupData() {
        // debounce multiple requests
        if (didFetchStartupData) {
            return
        }
        didFetchStartupData = true

        thread(start = true, name = "PreloadedDataThread") {
            // quickly load data that we've already fetched
            loadExistingData()
        }

        thread(start = true, name = "StartUpFetchThread") {
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
    private fun loadExistingData() {
        // home page and notifications
        Profiler.measure(loggingChannel, "Populated default home data") {
            loadPageOfHomeFromDB(0)
        }

        // search page
        Profiler.measure(loggingChannel, "Populated search page") {
            loadPageOfDiscoveryFromDB()
        }
    }
    private fun loadPageOfHomeFromDB(page : Int = 0) {
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
    private fun loadPageOfDiscoveryFromDB() {
        val images = getDiscoveryViewsOfKind(PostKind.Image)
        discoverVM.setNewSubmissionsData(images)

        val writings = getDiscoveryViewsOfKind(PostKind.Writing)
        discoverVM.setNewWritingData(writings)

        val musics = getDiscoveryViewsOfKind(PostKind.Music)
        discoverVM.setNewMusicData(musics)
    }
    private fun fetchNotifications(forceReload: Boolean = false) : Promise {
        return FetchNotifications(db, requestHandler, loggingChannel, forceReload)
    }

    private fun fetchSubmissions(page : Int = 0,
        pageSize : Int = 48,
        forceReload : Boolean = false
    ) : Promise {
        return FetchPageOfHome(db, requestHandler, loggingChannel, page, pageSize, forceReload)
    }

    private fun fetchDiscovery() : Promise {
        return FetchPageOfDiscovery(db, requestHandler, loggingChannel, false).then(fun(_ : Any?) {
            loadPageOfDiscoveryFromDB()
        }, fun(failureReason : Any?) {
            loggingChannel.logError("Failed to fetch and set Discovery content with error : $failureReason")
        })
    }

    private fun fetchLatestHomePagePost(post : IHomePageContent) {
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

    override fun favoritePost(imagePost : HomePageImagePost) : Promise {
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

    override fun markNotificationsAsRead() {
        db.notificationsDao().markNotificationAsSeen()
        notesVM.updateUnreadNotifications(0)
    }

    override fun fetchSearchPage(keyword : String, options : SearchOptions) : Promise {
        return FetchPageOfSearch(db, keyword, options).then(fun(listOfPosts : Any?) {
            searchVM.setSearchQuery(keyword)
            searchVM.setSearchParameters(options)
            searchVM.setSearchResults(listOfPosts as List<HomePageImagePost>)
            //searchVM.SearchResultsReady.fire(listOfPosts as List<HomePageImagePost>)
        }, fun(failureToFetchDetails : Any?) {
            loggingChannel.logError("Failed to fetch page of search with reason : $failureToFetchDetails")
        })
    }


    companion object : IManagerAccessor<ContentManager> {
        private lateinit var instance : ContentManager
        override fun get(): ContentManager {
            return instance
        }

        fun init(
            db : AppDatabase,
            requestHandler : RequestHandler,
            loggingChannel: LoggingChannel
        ) {
            instance = ContentManager(
                db = db,
                requestHandler = requestHandler,
                loggingChannel = loggingChannel
            )
        }
    }
}