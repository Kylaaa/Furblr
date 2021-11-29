package com.itreallyiskyler.furblr.ui.home

import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.util.ContentManager
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList

class HomeViewModel : ViewModel() {
    init {
        ContentManager.HomePageContentReady.connect { content -> posts.loadData(content) }
        ContentManager.HomePageContentUpdated.connect { index, post -> posts.reloadDataEntry(index, post) }
        ContentManager.fetchSubmissions(forceReload = false)
    }

    // TODO : figure out how to page this nonsense
    val posts = SynchronizedLiveDataList<IHomePageContent>(emptyList())
}