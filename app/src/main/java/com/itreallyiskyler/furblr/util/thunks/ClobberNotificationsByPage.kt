package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.Post
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.ui.notifications.NotificationsPagePost
import java.lang.IndexOutOfBoundsException

fun ClobberNotificationsByPage(dbImpl : AppDatabase,
                                page : Int = 0,
                                pageSize : Int = 40) : List<NotificationsPagePost> {
    val notificationsDao = dbImpl.notificationsDao()
    val postsDao = dbImpl.postsDao()
    val usersDao = dbImpl.usersDao()

    val notes : MutableList<NotificationsPagePost> = mutableListOf()

    val notifications = notificationsDao.getNotificationPage(pageSize, page * pageSize)
    for (note in notifications) {
        var creator : User? = null
        val users = usersDao.getExistingUsersForUsernames(listOf(note.senderId))
        if (users.size > 0) {
            creator = users[0]
        }

        var post : Post? = null
        if (note.kind == NotificationId.Watch.id ||
            note.kind == NotificationId.Shout.id) {
            // there is no associated post
        }
        else if (note.kind == NotificationId.Favorite.id ||
            note.kind == NotificationId.SubmissionComment.id ||
            note.kind == NotificationId.SubmissionCommentReply.id) {
            val posts = postsDao.getExistingPostsWithIds(listOf(note.hostId!!))
            if (posts.size > 0) {
                post = posts[0]
            }
        }
        else {
            throw IndexOutOfBoundsException("Behavior undefined for current notification kind.")
        }

        notes.add(NotificationsPagePost(note, creator, post))
    }

    return notes.sortedByDescending { it.notification.date }.toList()
}