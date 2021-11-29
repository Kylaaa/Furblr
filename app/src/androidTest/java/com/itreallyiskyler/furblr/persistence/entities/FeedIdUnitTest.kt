package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class FeedIdUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(FeedId::class.java.simpleName.lowercase(), FEED_TABLE_NAME)
    }
}