package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*

const val NOTIFICATIONS_TABLE_NAME = "notification"
const val NOTIFICATIONS_COLUMN_NAME_ID = "id"
const val NOTIFICATIONS_COLUMN_NAME_KIND = "kind"
const val NOTIFICATIONS_COLUMN_NAME_HOST_ID = "hostId"
const val NOTIFICATIONS_COLUMN_NAME_SENDER_ID = "senderId"
const val NOTIFICATIONS_COLUMN_NAME_DATE = "date"
const val NOTIFICATIONS_COLUMN_NAME_SEEN = "hasBeenSeen"

@Entity
data class Notification(
    @PrimaryKey @ColumnInfo(name = "$NOTIFICATIONS_COLUMN_NAME_ID") var id: Long,
    @ColumnInfo(name = "$NOTIFICATIONS_COLUMN_NAME_KIND") var kind : Int,
    @ColumnInfo(name = "$NOTIFICATIONS_COLUMN_NAME_HOST_ID") var hostId: Long?,
    @ColumnInfo(name = "$NOTIFICATIONS_COLUMN_NAME_SENDER_ID") var senderId: String,
    @ColumnInfo(name = "$NOTIFICATIONS_COLUMN_NAME_DATE") var date: String,
    @ColumnInfo(name = "$NOTIFICATIONS_COLUMN_NAME_SEEN") var hasBeenSeen: Boolean = false
)
