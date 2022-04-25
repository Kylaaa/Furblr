package com.itreallyiskyler.furblr.ui.home

import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.persistence.entities.*

data class HomePageImagePost(
    val postData : View,
    val postCreator : User,
    val postTags : List<Tag>,
    val postComments : List<Comment>,
    override val postKind: PostKind = PostKind.fromId(postData.kind),
    override val contentId : Long = postData.id,
    override val postDate: String = postData.date) : IHomePageContent
