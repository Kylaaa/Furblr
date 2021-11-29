package com.itreallyiskyler.furblr.ui.home

import com.itreallyiskyler.furblr.enum.PostKind

interface IHomePageContent {
    val contentId : Long
    val postKind : PostKind
    val postDate : String
}