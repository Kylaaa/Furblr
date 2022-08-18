package com.itreallyiskyler.testhelpers.managers

import android.content.Context
import android.webkit.WebView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.managers.*
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.discover.DiscoverViewModel
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.HomeViewModel
import com.itreallyiskyler.furblr.ui.notifications.NotificationsViewModel
import com.itreallyiskyler.furblr.ui.search.SearchViewModel
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import com.itreallyiskyler.furblr.util.Signal
import okhttp3.Request
import java.net.URL

object SingletonInitializer {
    fun init(
        authImpl : IAuthManager = stubAuthManager(),
        contentImpl : IContentManager = stubContentManager(),
        databaseImpl : IDBManager = stubDBManager(),
        loggingManager: ILoggingManager = stubLoggingManager(),
        networkingManager: INetworkingManager = stubNetworkingManager()
    ) {
        SingletonManager.init(
            am = authImpl,
            cm = contentImpl,
            dbm = databaseImpl,
            lm = loggingManager,
            nm = networkingManager,
        )
    }

    // NOTE - NONE OF THESE IMPLEMENTATIONS ARE MEANT TO BE USEFUL FOR TESTS
    // PLEASE OVERRIDE AS NEEDED
    private fun stubAuthManager() : IAuthManager {
        return object : IAuthManager {
            override val UserLoggedIn = Signal<Unit>()
            override val UserLoggedOut = Signal<Unit>()
            override fun enableCookieSettingsOnWebView(wv: WebView) {}
            override fun isAuthenticated(): Boolean {
                return true
            }
            override fun logout() {}
            override fun syncWebviewCookies(wv: WebView) {}
        }
    }

    private fun stubContentManager() : IContentManager {
        return object : IContentManager {
            override val discoverVM: DiscoverViewModel = DiscoverViewModel()
            override val homeVM: HomeViewModel = HomeViewModel()
            override val notesVM: NotificationsViewModel = NotificationsViewModel()
            override val searchVM: SearchViewModel = SearchViewModel()

            override fun favoritePost(imagePost: HomePageImagePost): Promise {
                return Promise.resolve("")
            }
            override fun markNotificationsAsRead() {}
            override fun fetchStartupData() {}
            override fun fetchSearchPage(keyword: String, options: SearchOptions): Promise {
                return Promise.resolve("")
            }
        }
    }

    private fun stubDBManager() : IDBManager {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return object : IDBManager {

            private val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "furblr-testing-db"
            ).build()

            override fun getDB(): AppDatabase {
                return db
            }
            override fun resetDB() {
                db.clearAllTables()
            }
        }
    }

    private fun stubLoggingManager() : ILoggingManager {
        return object : ILoggingManager {
            override fun createChannel(channelName: String?, level: LogLevel, handler: ((Any?) -> Unit)?): LoggingChannel {
                return LoggingChannel("LoggingManagerStub", LogLevel.NONE)
            }
            override fun log(level: LogLevel, message: Any?) {}
            override fun setLogLevel(level: LogLevel) {}
        }
    }

    private fun stubNetworkingManager() : INetworkingManager {
        return object : INetworkingManager {
            override val logChannel: LoggingChannel = LoggingChannel("NetworkingManagerStub", LogLevel.NONE)
            override val requestHandler: RequestHandler = { url : URL, _ : Request, _ : GenericCallback, _ : GenericCallback ->
                throw Exception("Unimplemented request to $url")
            }
        }
    }
}