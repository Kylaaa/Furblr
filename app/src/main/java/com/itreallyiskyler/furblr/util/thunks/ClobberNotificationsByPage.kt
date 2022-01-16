package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.Notification
import com.itreallyiskyler.furblr.persistence.entities.Post
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.ui.notifications.NotificationsPagePost
import com.itreallyiskyler.furblr.util.DateFormatter
import okhttp3.internal.toImmutableList
import okhttp3.internal.toImmutableMap
import java.lang.IndexOutOfBoundsException
import java.util.*

fun ClobberNotificationsByPage(dbImpl : AppDatabase,
                                page : Int = 0,
                                pageSize : Int = 40) : List<NotificationsPagePost> {
    // organize clusters of notifications by date
    val notificationsDao = dbImpl.notificationsDao()
    val postsDao = dbImpl.postsDao()
    val usersDao = dbImpl.usersDao()


    val notesByDate : MutableMap<String, MutableList<Notification>> = mutableMapOf()
    val userByNote  : MutableMap<Notification, User> = mutableMapOf()
    val postByNote  : MutableMap<Notification, Post> = mutableMapOf()

    // first pass, group notifications by date and group similar data
    val notifications = notificationsDao.getNotificationPage(pageSize, page * pageSize)
    for (note in notifications) {
        // check if we have the user already downloaded
        val users = usersDao.getExistingUsersForUsernames(listOf(note.senderId))
        if (users.isNotEmpty()) {
            userByNote[note] = users[0]
        }

        // check if he have access to the original post
        if (note.kind == NotificationId.Watch.id ||
            note.kind == NotificationId.Shout.id) {
            // there is no associated post
        }
        else if (note.kind == NotificationId.Favorite.id ||
            note.kind == NotificationId.SubmissionComment.id ||
            note.kind == NotificationId.SubmissionCommentReply.id) {
            val posts = postsDao.getExistingPostsWithIds(listOf(note.hostId!!))
            if (posts.size > 0) {
                postByNote[note] = posts[0]
            }
        }
        else {
            throw IndexOutOfBoundsException("Behavior undefined for current notification kind.")
        }

        // create a date key and hold onto this notification
        val truncatedDate = note.date.substring(0, 10)
        if (notesByDate[truncatedDate] == null) {
            notesByDate[truncatedDate] = mutableListOf()
        }
        notesByDate[truncatedDate]!!.add(note)
    }

    // second pass, create properly formatted objects
    val notes : MutableList<NotificationsPagePost> = mutableListOf()
    notesByDate.forEach { dateString, notesList ->
        run {
            val usersDict : MutableMap<Notification, User> = mutableMapOf()
            val postsDict : MutableMap<Notification, Post> = mutableMapOf()
            notesList.forEach {
                if (userByNote[it] != null) {
                    usersDict[it] = userByNote[it]!!
                }

                if (postByNote[it] != null) {
                    postsDict[it] = postByNote[it]!!
                }
            }

            notes.add(
                NotificationsPagePost(
                    dateString,
                    notesList.toImmutableList(),
                    postsDict.toImmutableMap(),
                    usersDict.toImmutableMap()
                )
            )
        }
    }

    return notes.sortedByDescending { it.date }.toList()
}