package com.itreallyiskyler.furblr.ui.home

import com.itreallyiskyler.furblr.persistence.entities.Comment
import com.itreallyiskyler.furblr.persistence.entities.Post
import com.itreallyiskyler.furblr.persistence.entities.Tag
import com.itreallyiskyler.furblr.persistence.entities.User

data class HomePagePost(
    val postData : Post,
    val postCreator : User,
    val postTags : List<Tag>,
    val postComments : List<Comment>)
