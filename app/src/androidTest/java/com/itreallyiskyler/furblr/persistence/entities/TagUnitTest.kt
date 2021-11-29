package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class TagUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(Tag::class.java.simpleName.lowercase(), TAGS_TABLE_NAME)
    }
}