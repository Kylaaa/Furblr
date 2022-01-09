package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class NotificationUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(Notification::class.java.simpleName.lowercase(), NOTIFICATIONS_TABLE_NAME)
    }
}