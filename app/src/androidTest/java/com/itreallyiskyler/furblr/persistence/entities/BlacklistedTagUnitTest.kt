package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class BlacklistedTagUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(BlacklistedTag::class.java.simpleName.lowercase(), BLACKLISTED_TAGS_TABLE_NAME)
    }
}