package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.EntitiesTestClass
import com.itreallyiskyler.furblr.persistence.entities.*

class CommentUnitTest : EntitiesTestClass(Comment::class.java, COMMENTS_TABLE_NAME) {
}