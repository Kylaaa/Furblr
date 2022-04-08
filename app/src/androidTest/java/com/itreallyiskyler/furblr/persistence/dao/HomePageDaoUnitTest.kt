package com.itreallyiskyler.furblr.persistence.dao

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.persistence.DBTestClass
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.furblr.util.DateFormatter
import org.junit.Assert
import org.junit.Test

class HomePageDaoUnitTest : DBTestClass() {

    private fun populateDB(db : AppDatabase) {
        val testUserA = User("UserA", "a", 2001, "test user account", DateFormatter.createDate(1999, 1, 1, 0, 0))
        val testUserB = User("UserB", "b", 2002, "test user account", DateFormatter.createDate(1999, 2, 1, 0, 0))
        val usersDao = db.usersDao()
        usersDao.insertOrUpdateUsers(testUserA, testUserB)

        val testPostA = View(1, testUserA.username, "Title 1", "Description 1", DateFormatter.createDate(2000, 1, 1, 0, 0), "General", "testUrl/123", 0, 0, 0, "key123", false, PostKind.Image.id, null, null)
        val testPostB = View(2, testUserA.username, "Title 2", "Description 2", DateFormatter.createDate(2000, 2, 1, 0, 0), "Mature", "testUrl/123", 0, 0, 0, "key456", false, PostKind.Image.id, null, null)
        val testPostC = View(3, testUserB.username, "Title 3", "Description 3", DateFormatter.createDate(2000, 3, 1, 0, 0), "Adult", "testUrl/123", 0, 0, 0, "key789", false, PostKind.Image.id, null, null)
        val viewsDao = db.viewsDao()
        viewsDao.insertOrUpdate(testPostA, testPostB, testPostC)

        val feedIdA = FeedId.fromPost(ContentFeedId.Home, testPostA)
        val feedIdB = FeedId.fromPost(ContentFeedId.Search, testPostB)
        val feedIdC = FeedId.fromPost(ContentFeedId.Home, testPostC)
        val feedDao = db.contentFeedDao()
        feedDao.insertOrUpdate(feedIdA, feedIdB, feedIdC)

        val testCommentA1 = Comment(101, testPostA.id, CommentLocationId.Post.id, testUserA.username, "Comment 1", DateFormatter.createDate(2000, 1, 2, 1, 0))
        val testCommentB1 = Comment(102, testPostB.id, CommentLocationId.Post.id, testUserB.username, "Comment 2", DateFormatter.createDate(2000, 1, 5, 2, 0))
        val testCommentB2 = Comment(103, testPostB.id, CommentLocationId.Post.id, testUserB.username, "Comment 3", DateFormatter.createDate(2001, 1, 3, 3, 0))
        val testCommentB3 = Comment(104, testPostB.id, CommentLocationId.Post.id, testUserB.username, "Comment 4", DateFormatter.createDate(2001, 1, 3, 3, 0))
        val testCommentC1 = Comment(105, testPostC.id, CommentLocationId.Post.id, testUserB.username, "Comment 5", DateFormatter.createDate(2001, 1, 3, 3, 0))
        val commentsDao = db.commentsDao()
        commentsDao.insertOrUpdateComment(testCommentA1, testCommentB1, testCommentB2, testCommentB3, testCommentC1)

        val testTagsA1 = Tag(testPostA.id, "foo")
        val testTagsA2 = Tag(testPostA.id, "bar")
        val testTagsA3 = Tag(testPostA.id, "blah")
        val testTagsB1 = Tag(testPostB.id, "foo")
        val tagsDao = db.tagsDao()
        tagsDao.insertOrUpdateTag(testTagsA1, testTagsA2, testTagsA3, testTagsB1)
    }

    @Test
    fun getCommentsForPosts_returnsTheExpectedResults() {
        // TODO : MOVE THIS TO A NEW FILE
        val db : AppDatabase = getDB()
        val commentsDao : CommentsDao = db.commentsDao()
        populateDB(db)

        var posts : List<Long> = listOf(1) // testPostA
        var comments = commentsDao.getCommentsForPosts(posts)
        Assert.assertEquals(comments.size, 1)

        posts = listOf(2) // testPostB
        comments = commentsDao.getCommentsForPosts(posts)
        Assert.assertEquals(comments.size, 3)

        posts = listOf(1, 2) // testPostA, testPostB
        comments = commentsDao.getCommentsForPosts(posts)
        Assert.assertEquals(comments.size, 4)
    }

    @Test
    fun getTagsForPosts_returnsTheExpectedResults() {
        // TODO : MOVE THIS TO A NEW FILE
        val db : AppDatabase = getDB()
        val tagsDao : TagsDao = db.tagsDao()
        populateDB(db)

        val posts : List<Long> = listOf(1) // testPostA
        val tags = tagsDao.getTagsForPosts(posts)
        Assert.assertEquals(tags.size, 3)
        Assert.assertEquals(tags[0].tagContents, "foo")
        Assert.assertEquals(tags[1].tagContents, "bar")
        Assert.assertEquals(tags[2].tagContents, "blah")

    }
}