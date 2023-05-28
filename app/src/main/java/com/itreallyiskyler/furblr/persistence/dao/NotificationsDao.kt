package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.*
import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.persistence.entities.*

@Dao
interface NotificationsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrUpdateNotification(vararg notification: Notification)

    // NOTE - sqlite does not support boolean values, android-room wraps bools as integers
    // 1 = true
    // 0 = false
    @Query("UPDATE $NOTIFICATIONS_TABLE_NAME " +
            "SET $NOTIFICATIONS_COLUMN_NAME_SEEN = 1 " +
            "WHERE $NOTIFICATIONS_COLUMN_NAME_SEEN = 0")
    fun markNotificationAsSeen()

    @Query("SELECT * FROM $NOTIFICATIONS_TABLE_NAME " +
            "ORDER BY $NOTIFICATIONS_COLUMN_NAME_DATE DESC " +
            "LIMIT :pageSize " +
            "OFFSET :offset")
    fun getNotificationPage(pageSize: Int = 40, offset: Int = 0) : List<Notification>

    @Query("SELECT COUNT($NOTIFICATIONS_COLUMN_NAME_ID) " +
            "FROM $NOTIFICATIONS_TABLE_NAME " +
            "WHERE $NOTIFICATIONS_COLUMN_NAME_SEEN = 0")
    fun getUnreadNotificationCount() : Int

    @Query("SELECT * FROM $NOTIFICATIONS_TABLE_NAME")
    fun getAllNotifications(): List<Notification>
}