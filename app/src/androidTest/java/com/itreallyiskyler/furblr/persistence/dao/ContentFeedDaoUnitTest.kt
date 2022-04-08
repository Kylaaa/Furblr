package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.persistence.DBTestClass
import com.itreallyiskyler.furblr.persistence.dao.ContentFeedDao
import com.itreallyiskyler.furblr.persistence.dao.UsersDao
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.FeedId
import com.itreallyiskyler.furblr.persistence.entities.View
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.util.DateFormatter
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class ContentFeedDaoUnitTest : DBTestClass() {
    @Test
    fun insertOrUpdate_returnsExpectedResults() {
        val db : AppDatabase = getDB()
        val contentFeedDao : ContentFeedDao = db.contentFeedDao()

        // insert feed items
        val testFeedA = FeedId(ContentFeedId.Home.id, PostKind.Image.id, 123, DateFormatter.createDate(2000, 1, 1, 0, 0))
        val testFeedB = FeedId(ContentFeedId.Home.id, PostKind.Image.id, 456, DateFormatter.createDate(2000, 1, 2, 0, 0))
        val testFeedC = FeedId(ContentFeedId.Home.id, PostKind.Image.id, 789, DateFormatter.createDate(2000, 1, 3, 0, 0))
        contentFeedDao.insertOrUpdate(testFeedA, testFeedB, testFeedC)

        // fetch the items
        val feedItems = contentFeedDao.getAllFeedIds()

        // validate that it worked
        Assert.assertEquals(feedItems.size, 3)
    }

    @Test
    fun insertOrUpdate_duplicatesAreUpdated() {
        val db : AppDatabase = getDB()
        val contentFeedDao : ContentFeedDao = db.contentFeedDao()

        // insert feed items
        val testFeedA1 = FeedId(ContentFeedId.Home.id, PostKind.Image.id, 123, DateFormatter.createDate(2000, 1, 1, 0, 0))
        contentFeedDao.insertOrUpdate(testFeedA1)

        // fetch and validate the items
        val feedItems = contentFeedDao.getAllFeedIds()
        Assert.assertEquals(feedItems.size, 1)
        Assert.assertEquals(feedItems[0].feed, ContentFeedId.Home.id)
        Assert.assertEquals(feedItems[0].postKind, PostKind.Image.id)
        Assert.assertEquals(feedItems[0].postId, 123)
        Assert.assertEquals(feedItems[0].date, DateFormatter.createDate(2000, 1, 1, 0, 0))

        // insert the same item but with different values
        val testFeedA2 = FeedId(ContentFeedId.Home.id, PostKind.Image.id, 123, DateFormatter.createDate(2000, 1, 2, 0, 0))
        contentFeedDao.insertOrUpdate(testFeedA2)

        // validate that it worked
        val feedItems2 = contentFeedDao.getAllFeedIds()
        Assert.assertEquals(feedItems2.size, 1)
        Assert.assertEquals(feedItems2[0].feed, ContentFeedId.Home.id)
        Assert.assertEquals(feedItems2[0].postKind, PostKind.Image.id)
        Assert.assertEquals(feedItems2[0].postId, 123)
        Assert.assertEquals(feedItems2[0].date, DateFormatter.createDate(2000, 1, 2, 0, 0))
    }

    @Test
    fun insertOrUpdate_sameIdsInDifferentFeedsArePreserved() {
        val db : AppDatabase = getDB()
        val contentFeedDao : ContentFeedDao = db.contentFeedDao()

        // insert feed items
        val postId : Long = 123
        val postDate : String = DateFormatter.createDate(2000, 1, 1, 0, 0)
        val testFeedA = FeedId(ContentFeedId.Home.id, PostKind.Image.id, postId, postDate)
        val testFeedB = FeedId(ContentFeedId.Search.id, PostKind.Image.id, postId, postDate)
        contentFeedDao.insertOrUpdate(testFeedA)
        contentFeedDao.insertOrUpdate(testFeedB)

        // fetch and validate the items
        val feedItems = contentFeedDao.getAllFeedIds()
        Assert.assertEquals(feedItems.size, 2)
    }

    @Test
    fun insertOrUpdate_sameIdsInDifferentPostKindsArePreserved() {
        val db : AppDatabase = getDB()
        val contentFeedDao : ContentFeedDao = db.contentFeedDao()

        // insert feed items
        val postId : Long = 123
        val testFeedA = FeedId(ContentFeedId.Home.id, PostKind.Image.id, postId, DateFormatter.createDate(2000, 1, 1, 0, 0))
        val testFeedB = FeedId(ContentFeedId.Home.id, PostKind.Journal.id, postId, DateFormatter.createDate(2000, 1, 2, 0, 0))
        contentFeedDao.insertOrUpdate(testFeedA)
        contentFeedDao.insertOrUpdate(testFeedB)

        // fetch and validate the items
        val feedItems = contentFeedDao.getAllFeedIds()
        Assert.assertEquals(feedItems.size, 2)
    }

    @Test
    fun getPageFromFeed_fetchesPostsFromTheCorrectFeed() {
        val db : AppDatabase = getDB()
        val contentFeedDao : ContentFeedDao = db.contentFeedDao()

        // insert feed items
        val testFeedA = FeedId(ContentFeedId.Home.id, PostKind.Image.id, 1, DateFormatter.createDate(2000, 1, 1, 0, 0))
        val testFeedB = FeedId(ContentFeedId.Home.id, PostKind.Journal.id, 2, DateFormatter.createDate(2000, 1, 2, 0, 0))
        val testFeedC = FeedId(ContentFeedId.Search.id, PostKind.Image.id, 3, DateFormatter.createDate(2000, 1, 3, 0, 0))
        contentFeedDao.insertOrUpdate(testFeedA, testFeedB, testFeedC)

        // fetch and validate the items
        val homeFeedItems = contentFeedDao.getPageFromFeed(listOf(ContentFeedId.Home.id))
        Assert.assertEquals(homeFeedItems.size, 2)
        Assert.assertEquals(homeFeedItems[0].feed, ContentFeedId.Home.id)
        Assert.assertEquals(homeFeedItems[1].feed, ContentFeedId.Home.id)

        val searchFeedItems = contentFeedDao.getPageFromFeed(listOf(ContentFeedId.Search.id))
        Assert.assertEquals(searchFeedItems.size, 1)
        Assert.assertEquals(searchFeedItems[0].feed, ContentFeedId.Search.id)
    }

    @Test
    fun getPageFromFeed_postsAreInDescendingOrderByDate() {
        val db : AppDatabase = getDB()
        val contentFeedDao : ContentFeedDao = db.contentFeedDao()

        // create a lot of posts
        val pageSize = 100
        for (id in 1 .. pageSize) {
            val year = 2000 + Random.nextInt(0, 20)
            val month = Random.nextInt(1, 12)
            val day = Random.nextInt(1, 28)
            val hour = Random.nextInt(0, 23)
            val minute = Random.nextInt(0, 59)
            val date = DateFormatter.createDate(year, month, day, hour, minute)
            val kind = if (Random.nextBoolean()) PostKind.Image.id else PostKind.Journal.id
            val testFeedId = FeedId(ContentFeedId.Home.id, kind, id.toLong(), date)
            contentFeedDao.insertOrUpdate(testFeedId)
        }

        // get a page of posts
        val homeFeedItems = contentFeedDao.getPageFromFeed(listOf(ContentFeedId.Home.id), pageSize)
        Assert.assertEquals(homeFeedItems.size, pageSize)

        // check that every post is in proper chronological order
        var previousDate = homeFeedItems[0].date
        for (i : Int in 1 .. (homeFeedItems.size - 1)) {
            val nextDate = homeFeedItems[i].date
            val comparison = previousDate.compareTo(nextDate)
            Assert.assertTrue("Failed when comparing $previousDate to $nextDate = $comparison", comparison > 0)
            previousDate = nextDate
        }
    }
}