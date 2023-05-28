package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.ui.home.HomePageTextPost

fun ClobberHomePageTextsById(assetIds : List<Long>) : List<HomePageTextPost> {
    val dbImpl = SingletonManager.get().DBManager.getDB()
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
            try {
                val postCreator = users.find { user -> user.username == post.profileId }
                val postComments = journalComments.filter { comment -> comment.hostId == post.id }

                val hpp = HomePageTextPost(post, postCreator!!, postComments)
                homePageTextPosts.add(hpp)
            }
            catch (ex : Exception)
            {
                SingletonManager.get().LoggingManager.getChannel().logError(
                    "Failed to attribute user to journal #${post.id}")
            }
        }
    }

    return homePageTextPosts.sortedByDescending { it.postData.date }.toList()
}