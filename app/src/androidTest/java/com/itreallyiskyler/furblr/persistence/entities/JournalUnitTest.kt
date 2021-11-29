package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class JournalUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(Journal::class.java.simpleName.lowercase(), JOURNALS_TABLE_NAME)
    }
}