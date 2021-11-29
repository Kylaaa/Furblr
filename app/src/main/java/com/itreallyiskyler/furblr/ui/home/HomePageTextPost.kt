package com.itreallyiskyler.furblr.ui.home

import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.persistence.entities.*

data class HomePageTextPost(
    val postData : Journal,
    val postCreator : User,
    val postComments : List<Comment>,
    override val postKind: PostKind = PostKind.Text,
    override val contentId : Long = postData.id,
    override val postDate : String = postData.date) : IHomePageContent
