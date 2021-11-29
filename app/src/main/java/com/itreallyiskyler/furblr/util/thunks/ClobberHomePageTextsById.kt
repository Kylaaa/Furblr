package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.home.HomePageTextPost

fun ClobberHomePageTextsById(dbImpl : AppDatabase,
                             assetIds : List<Long>) : List<HomePageTextPost> {
    val commentsDao = dbImpl.commentsDao()
    val journalsDao = dbImpl.journalsDao()
    val usersDao = dbImpl.usersDao()

    val journals = journalsDao.getExistingJournalsWithIds(assetIds)
    val journalIds = journals.map { journal -> journal.id }
    val creatorIds = journals.map { journal -> journal.profileId }
    val journalComments = commentsDao.getCommentsForPosts(journalIds, CommentLocationId.Journal.id)
    val users = usersDao.getExistingUsersForUsernames(creatorIds)

    // clobber together the data
    val homePageTextPosts: MutableList<HomePageTextPost> = mutableListOf()
    journals.forEach { post ->
        run {
            val postCreator = users.find { user -> user.username == post.profileId }!!
            val postComments = journalComments.filter { comment -> comment.hostId == post.id }

            val hpp = HomePageTextPost(post, postCreator, postComments)
            homePageTextPosts.add(hpp)
        }
    }

    return homePageTextPosts.sortedByDescending { it.postData.date }.toList()
}