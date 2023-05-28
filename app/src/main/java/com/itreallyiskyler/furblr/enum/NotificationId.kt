package com.itreallyiskyler.furblr.enum

enum class NotificationId(override val id : Int) : IValueAccessor<Int> {
    SubmissionComment(0),
    SubmissionCommentReply(1),
    Shout(2),
    Favorite(3),
    Watch(4)
}