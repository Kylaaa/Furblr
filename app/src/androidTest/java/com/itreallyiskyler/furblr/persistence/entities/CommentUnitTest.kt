package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.testhelpers.persistence.EntitiesTestClass

class CommentUnitTest : EntitiesTestClass(Comment::class.java, COMMENTS_TABLE_NAME) {
}