package com.itreallyiskyler.furblr.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.util.CommandWithArgs1
import com.itreallyiskyler.furblr.util.ContentManager

class HomeViewModel : ViewModel() {
    init {
        ContentManager.HomePageContentReady.connect(object : CommandWithArgs1<Unit, List<HomePagePost>> {
            override fun invoke(posts : List<HomePagePost>) { loadPosts() }
        })
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _posts : MutableLiveData<List<HomePagePost>> by lazy {
        MutableLiveData<List<HomePagePost>>().also {
            loadPosts()
        }
    }

    // TODO : figure out how to page this nonsense
    fun getPosts() : LiveData<List<HomePagePost>> {
        return _posts
    }
    private fun loadPosts()
    {
       _posts.value = ContentManager.getHomePagePosts()
    }

}