package com.itreallyiskyler.furblr.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.Signal
import com.itreallyiskyler.furblr.util.SynchronizedLiveData
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList

class SearchViewModel : ViewModel() {
    val SearchResultsReady = Signal<List<IHomePageContent>>()

    val searchQuery = SynchronizedLiveData<String>("")
    val searchParameters = SynchronizedLiveData<SearchOptions>(SearchOptions())
    val searchResults = SynchronizedLiveDataList<IHomePageContent>(listOf())

    fun setSearchQuery(newQuery : String) {
        searchQuery.loadData(newQuery)
    }

    fun setSearchParameters(newParameters : SearchOptions) {
        searchParameters.loadData(newParameters)
    }

    fun setSearchResults(content : List<IHomePageContent>) {
        searchResults.loadData(content)
    }

}