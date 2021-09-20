package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*

const val COMMENTS_TABLE_NAME = "comment"
const val COMMENTS_COLUMN_NAME_ID = "id"
const val COMMENTS_COLUMN_NAME_POST_ID = "postId"
const val COMMENTS_COLUMN_NAME_SENDER_ID = "senderId"
const val COMMENTS_COLUMN_NAME_CONTENTS = "shoutContents"
const val COMMENTS_COLUMN_NAME_DATE = "date"

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(entity = Post::class,
            parentColumns = arrayOf("$POSTS_COLUMN_NAME_ID"),
            childColumns = arrayOf("$COMMENTS_COLUMN_NAME_POST_ID"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(
        Index(COMMENTS_COLUMN_NAME_POST_ID)
    )
)
data class Comment(
    @PrimaryKey @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_ID") var id: Long,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_POST_ID") var postId: Long,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_SENDER_ID") var senderId : String,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_CONTENTS") var contents: String,
    @ColumnInfo(name = "$COMMENTS_COLUMN_NAME_DATE") var date: String,
)
