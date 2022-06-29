package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class PostUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(View::class.java.simpleName.lowercase(), VIEW_TABLE_NAME)
    }
}