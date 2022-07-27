package com.itreallyiskyler.furblr.ui.home

import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.util.Signal
import com.itreallyiskyler.furblr.util.Signal2
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList

class HomeViewModel : ViewModel() {
    val HomePageContentReady = Signal<List<IHomePageContent>>()
    val HomePageContentUpdated = Signal2<Int, IHomePageContent>()
    val posts = SynchronizedLiveDataList<IHomePageContent>(listOf())

    // helper functions
    private var homePagePosts : MutableList<IHomePageContent> = mutableListOf()
    private var homePageIndices : HashMap<Long, Int> = hashMapOf()
    private fun indexHomePagePosts(imagePosts : List<IHomePageContent>){
        for (i in imagePosts.indices) {
            val post = imagePosts[i]
            homePageIndices[post.contentId] = i
        }
    }

    fun setPosts(content : List<IHomePageContent>)
    {
        homePagePosts = content.toMutableList()
        indexHomePagePosts(homePagePosts)
        posts.loadData(content)
        HomePageContentReady.fire(homePagePosts.toList())
    }

    fun updatePost(postId : Long, content : IHomePageContent) {
        if (!homePageIndices.containsKey(postId)) {
            throw IndexOutOfBoundsException("Could not find $postId in homePageIndices")
        }

        val index: Int = homePageIndices[postId]!!
        homePagePosts[index] = content

        posts.reloadDataEntry(index, content)
        HomePageContentUpdated.fire(index, content)
    }
}