package com.itreallyiskyler.furblr.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.util.CommandWithArgs1
import com.itreallyiskyler.furblr.util.ContentManager
import okhttp3.internal.notifyAll

class HomeViewModel : ViewModel() {
    init {
        ContentManager.HomePageContentReady.connect { posts -> loadPosts(posts) }
        ContentManager.fetchSubmissions(forceReload = false)
    }

    // TODO : figure out how to page this nonsense
    private val _posts = MutableLiveData<List<HomePagePost>>();
    val posts : LiveData<List<HomePagePost>>
        get() = _posts

    private fun loadPosts(updatedList : List<HomePagePost>)
    {
        _posts.postValue(updatedList)
        synchronized(posts){
            posts.notifyAll()
        }
    }

}