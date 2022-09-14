package com.itreallyiskyler.furblr.enum

enum class ContentFeedId(override val id : Int) : IValueAccessor<Int> {
    Home(0),
    Search(1),
    Discover(2),
    Other(3)
}