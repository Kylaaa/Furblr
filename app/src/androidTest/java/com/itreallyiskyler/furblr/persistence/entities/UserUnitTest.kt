package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import org.junit.Assert
import org.junit.Test

class UserUnitTest {
    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(User::class.java.simpleName.lowercase(), USERS_TABLE_NAME)
    }
}