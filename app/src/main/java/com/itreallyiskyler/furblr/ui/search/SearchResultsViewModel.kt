package com.itreallyiskyler.furblr.ui.search

import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.Signal
import com.itreallyiskyler.furblr.util.Signal2
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList

class SearchResultsViewModel : ViewModel() {

    val DiscoverPageContentReady = Signal<List<IHomePageContent>>()
    val DiscoverPageContentUpdated = Signal2<Int, IHomePageContent>()
    val posts = SynchronizedLiveDataList<IHomePageContent>(listOf())

    // helper functions
    private var discoverPagePosts : MutableList<IHomePageContent> = mutableListOf()
    private var discoverPageIndices : HashMap<Long, Int> = hashMapOf()
    private fun indexDiscoverPagePosts(imagePosts : List<IHomePageContent>){
        for (i in imagePosts.indices) {
            val post = imagePosts[i]
            discoverPageIndices[post.contentId] = i
        }
    }

    fun setPosts(content : List<IHomePageContent>)
    {
        discoverPagePosts = content.toMutableList()
        indexDiscoverPagePosts(discoverPagePosts)
        posts.loadData(content)
        DiscoverPageContentReady.fire(discoverPagePosts.toList())
    }

    fun updatePost(postId : Long, content : IHomePageContent) {
        if (!discoverPageIndices.containsKey(postId)) {
            throw IndexOutOfBoundsException("Could not find $postId in homePageIndices")
        }

        val index: Int = discoverPageIndices[postId]!!
        discoverPagePosts[index] = content

        posts.reloadDataEntry(index, content)
        DiscoverPageContentUpdated.fire(index, content)
    }
}