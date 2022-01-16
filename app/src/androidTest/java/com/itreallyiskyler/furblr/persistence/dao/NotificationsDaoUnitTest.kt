package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.enum.AgeRating
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.persistence.DBTestClass
import com.itreallyiskyler.furblr.persistence.dao.ContentFeedDao
import com.itreallyiskyler.furblr.persistence.dao.NotificationsDao
import com.itreallyiskyler.furblr.persistence.dao.UsersDao
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.FeedId
import com.itreallyiskyler.furblr.persistence.entities.Notification
import com.itreallyiskyler.furblr.persistence.entities.Post
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.util.DateFormatter
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class NotificationsDaoUnitTest : DBTestClass() {
    @Test
    fun insertOrUpdate_returnsExpectedResults() {
        val db : AppDatabase = getDB()
        val notificationsDao : NotificationsDao = db.notificationsDao()

        val testUserA = User("TestUserA", "a", 123, "foo", DateFormatter.createDate(2000, 1, 1, 1, 1))
        val testPostA = Post(12345, testUserA.username, "Test Post A", "this is a test", "23456", 0, 1, 1, AgeRating.Adult.name, "98765", false, DateFormatter.createDate(2001, 1, 1, 1, 1))

        // insert notifications
        val testNotificationA = Notification(1, NotificationId.Favorite.id, testPostA.id, testUserA.username, DateFormatter.createDate(2000, 1, 1, 1, 1), false)
        notificationsDao.insertOrUpdateNotification(testNotificationA)

        // fetch the items
        val notes = notificationsDao.getAllNotifications()

        // validate that it worked
        Assert.assertEquals(notes.size, 1)
    }

    @Test
    fun insertOrUpdate_duplicatesAreIgnored() {
        val db : AppDatabase = getDB()
        val notificationsDao : NotificationsDao = db.notificationsDao()

        val testUserA = User("TestUserA", "a", 123, "foo", DateFormatter.createDate(2000, 1, 1, 1, 1))
        val testPostA = Post(12345, testUserA.username, "Test Post A", "this is a test", "23456", 0, 1, 1, AgeRating.Adult.name, "98765", false, DateFormatter.createDate(2001, 1, 1, 1, 1))

        // insert notifications
        val testNotificationA = Notification(0, NotificationId.Favorite.id, testPostA.id, testUserA.username, DateFormatter.createDate(2000, 1, 1, 1, 1), true)
        notificationsDao.insertOrUpdateNotification(testNotificationA)

        // fetch and validate the items
        val notes = notificationsDao.getAllNotifications()
        Assert.assertEquals(notes.size, 1)
        Assert.assertEquals(notes[0].hostId, testPostA.id)
        Assert.assertEquals(notes[0].hasBeenSeen, true)
        Assert.assertEquals(notes[0].kind, NotificationId.Favorite.id)
        Assert.assertEquals(notes[0].date, DateFormatter.createDate(2000, 1, 1, 1, 1))

        // insert the same item but with the `hasBeenSeen` field marked as false
        val testNotificationB = Notification(0, NotificationId.Favorite.id, testPostA.id, testUserA.username, DateFormatter.createDate(2001, 1, 1, 1, 1), false)
        notificationsDao.insertOrUpdateNotification(testNotificationB)

        // validate that it worked, and the original is unchanged
        val notes2 = notificationsDao.getAllNotifications()
        Assert.assertEquals(notes2.size, 1)
        Assert.assertEquals(notes2[0].hostId, testPostA.id)
        Assert.assertEquals(notes2[0].hasBeenSeen, true)
        Assert.assertEquals(notes2[0].kind, NotificationId.Favorite.id)
        Assert.assertEquals(notes2[0].date, DateFormatter.createDate(2000, 1, 1, 1, 1))
    }

    @Test
    fun markNotificationAsSeen_worksAsExpected() {
        val db : AppDatabase = getDB()
        val notificationsDao : NotificationsDao = db.notificationsDao()

        val testUserA = User("TestUserA", "a", 123, "foo", DateFormatter.createDate(2000, 1, 1, 1, 1))
        val testPostA = Post(12345, testUserA.username, "Test Post A", "this is a test", "23456", 0, 1, 1, AgeRating.Adult.name, "98765", false, DateFormatter.createDate(2001, 1, 1, 1, 1))

        // insert notifications
        val testNotificationA = Notification(1, NotificationId.Favorite.id, testPostA.id, testUserA.username, DateFormatter.createDate(2000, 1, 1, 1, 1), false)
        val testNotificationB = Notification(2, NotificationId.Shout.id, testPostA.id, testUserA.username, DateFormatter.createDate(2000, 2, 1, 1, 1), false)
        val testNotificationC = Notification(3, NotificationId.SubmissionComment.id, testPostA.id, testUserA.username, DateFormatter.createDate(2000, 3, 1, 1, 1), false)
        notificationsDao.insertOrUpdateNotification(testNotificationA, testNotificationB, testNotificationC)

        // fetch and validate the items
        val notes = notificationsDao.getAllNotifications()
        Assert.assertEquals(notes[0].hasBeenSeen, false)
        Assert.assertEquals(notes[1].hasBeenSeen, false)
        Assert.assertEquals(notes[2].hasBeenSeen, false)

        // mark the notification as seen
        notificationsDao.markNotificationAsSeen()

        // validate that it worked
        val notes2 = notificationsDao.getAllNotifications()
        Assert.assertEquals(notes2[0].hasBeenSeen, true)
        Assert.assertEquals(notes2[1].hasBeenSeen, true)
        Assert.assertEquals(notes2[2].hasBeenSeen, true)
    }

}