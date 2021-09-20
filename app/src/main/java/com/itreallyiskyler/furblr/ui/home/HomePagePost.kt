package com.itreallyiskyler.furblr.ui.home

import com.itreallyiskyler.furblr.persistence.entities.Comment
import com.itreallyiskyler.furblr.persistence.entities.Post
import com.itreallyiskyler.furblr.persistence.entities.Tag

data class HomePagePost(
    val postData : Post,
    val postTags : List<Tag>,
    val postComments : List<Comment>)
