package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.dao.UsersDao
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.testhelpers.persistence.DBTestClass
import org.junit.Assert
import org.junit.Test

class UsersDaoUnitTest : DBTestClass() {
    private fun createTestUser(name : String, userId : Long) : User {
        return User(name, name, userId, "a test description", "Jul 19, 2020 04:14 PM")
    }

    @Test
    fun insertOrUpdate_returnsExpectedResults() {
        val db : AppDatabase = getDB()
        val usersDao : UsersDao = db.usersDao()

        // insert users
        val testUserA : User = createTestUser("A", 12345)
        val testUserB : User = createTestUser("B", 12346)
        val testUserC : User = createTestUser("C", 12347)
        usersDao.insertOrUpdateUsers(testUserA, testUserB, testUserC)

        // fetch the users
        val users = usersDao.getAllUsers()

        // validate that it worked
        Assert.assertEquals(users.size, 3)
        Assert.assertEquals(users[0], testUserA)
        Assert.assertEquals(users[1], testUserB)
        Assert.assertEquals(users[2], testUserC)
    }
}