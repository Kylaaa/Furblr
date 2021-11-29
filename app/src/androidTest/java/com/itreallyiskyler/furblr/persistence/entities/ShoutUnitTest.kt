package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class ShoutUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(Shout::class.java.simpleName.lowercase(), SHOUTS_TABLE_NAME)
    }
}