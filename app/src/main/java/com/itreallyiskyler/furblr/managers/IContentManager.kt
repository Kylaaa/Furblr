package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.ui.discover.DiscoverViewModel
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.HomeViewModel
import com.itreallyiskyler.furblr.ui.notifications.NotificationsViewModel
import com.itreallyiskyler.furblr.ui.search.SearchViewModel
import com.itreallyiskyler.furblr.util.Promise

interface IContentManager {
    val discoverVM : DiscoverViewModel
    val homeVM : HomeViewModel
    val notesVM : NotificationsViewModel
    val searchVM : SearchViewModel

    fun fetchStartupData()
    fun favoritePost(imagePost: HomePageImagePost): Promise
    fun markNotificationsAsRead()
    fun fetchSearchPage(keyword: String, options: SearchOptions): Promise
}