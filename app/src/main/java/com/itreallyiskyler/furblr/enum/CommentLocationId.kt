package com.itreallyiskyler.furblr.enum

enum class CommentLocationId(override val id : Int) : IValueAccessor<Int> {
    Post(0),
    Journal(1)
}