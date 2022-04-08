package com.itreallyiskyler.furblr.ui.discover

import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.Signal
import com.itreallyiskyler.furblr.util.Signal2
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList

class DiscoverViewModel : ViewModel() {

    val DiscoverSearchResultsReady = Signal<List<IHomePageContent>>()
    val DiscoverPageContentUpdated = Signal2<Int, IHomePageContent>()

    val discoverDataSets = listOf(
        Pair(R.string.ui_discover_section_submissions, SynchronizedLiveDataList<IHomePageContent>(listOf())),
        Pair(R.string.ui_discover_section_writing, SynchronizedLiveDataList<IHomePageContent>(listOf())),
        Pair(R.string.ui_discover_section_music, SynchronizedLiveDataList<IHomePageContent>(listOf())),
        Pair(R.string.ui_discover_section_crafting, SynchronizedLiveDataList<IHomePageContent>(listOf())),
    )
    val searchResults = SynchronizedLiveDataList<IHomePageContent>(listOf())

    // helper functions
    private var discoverPagePosts : MutableList<IHomePageContent> = mutableListOf()
    private var discoverPageIndices : HashMap<Long, Int> = hashMapOf()
    private fun indexDiscoverPagePosts(imagePosts : List<IHomePageContent>){
        for (i in imagePosts.indices) {
            val post = imagePosts[i]
            discoverPageIndices[post.contentId] = i
        }
    }

    private fun setDataSetPosts(index : Int, content : List<IHomePageContent>)
    {
        discoverPagePosts = content.toMutableList()
        indexDiscoverPagePosts(discoverPagePosts)
        discoverDataSets[index].second.loadData(content)
    }
    fun setNewSubmissionsData(content : List<IHomePageContent>){
        setDataSetPosts(0, content)
    }
    fun setNewWritingData(content : List<IHomePageContent>){
        setDataSetPosts(1, content)
    }
    fun setNewMusicData(content : List<IHomePageContent>){
        setDataSetPosts(2, content)
    }
    fun setNewCraftingData(content : List<IHomePageContent>){
        setDataSetPosts(3, content)
    }
}