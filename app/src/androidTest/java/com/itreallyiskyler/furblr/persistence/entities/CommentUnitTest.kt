package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class CommentUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(Comment::class.java.simpleName.lowercase(), COMMENTS_TABLE_NAME)
    }
}