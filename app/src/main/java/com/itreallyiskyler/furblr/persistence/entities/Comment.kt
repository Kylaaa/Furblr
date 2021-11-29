package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*

const val COMMENTS_TABLE_NAME = "comment"
const val COMMENTS_COLUMN_NAME_ID = "id"
const val COMMENTS_COLUMN_NAME_HOST_ID = "hostId"
const val COMMENTS_COLUMN_NAME_SOURCE_LOCATION = "sourceLocation"
const val COMMENTS_COLUMN_NAME_SENDER_ID = "senderId"
const val COMMENTS_COLUMN_NAME_CONTENTS = "contents"
const val COMMENTS_COLUMN_NAME_DATE = "date"

@Entity
data class Comment(
    @PrimaryKey @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_ID") var id: Long,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_HOST_ID") var hostId: Long,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_SOURCE_LOCATION") var sourceLocation : Int,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_SENDER_ID") var senderId : String,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_CONTENTS") var contents: String,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_DATE") var date: String,
)
