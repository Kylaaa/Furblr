package com.itreallyiskyler.testhelpers.managers

import com.itreallyiskyler.furblr.managers.IContentManager
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.ui.discover.DiscoverViewModel
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.HomeViewModel
import com.itreallyiskyler.furblr.ui.notifications.NotificationsViewModel
import com.itreallyiskyler.furblr.ui.search.SearchViewModel
import com.itreallyiskyler.furblr.util.Promise

open class MockContentManager : IContentManager {
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