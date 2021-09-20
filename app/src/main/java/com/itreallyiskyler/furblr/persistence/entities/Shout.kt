package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*

const val SHOUTS_TABLE_NAME = "shout"
const val SHOUTS_COLUMN_NAME_ID = "id"
const val SHOUTS_COLUMN_NAME_PROFILE_ID = "profileId"
const val SHOUTS_COLUMN_NAME_SENDER_ID = "senderId"
const val SHOUTS_COLUMN_NAME_CONTENTS = "shoutContents"
const val SHOUTS_COLUMN_NAME_DATE = "date"

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("$USERS_COLUMN_NAME_ID"),
            childColumns = arrayOf("$SHOUTS_COLUMN_NAME_PROFILE_ID"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(
        Index(SHOUTS_COLUMN_NAME_PROFILE_ID)
    )
)
data class Shout(
    @PrimaryKey @ColumnInfo(name = "$SHOUTS_COLUMN_NAME_ID") var id: Long,
    @ColumnInfo(name = "$SHOUTS_COLUMN_NAME_PROFILE_ID") var profileId: String,
    @ColumnInfo(name = "$SHOUTS_COLUMN_NAME_SENDER_ID") var senderId : String,
    @ColumnInfo(name = "$SHOUTS_COLUMN_NAME_CONTENTS") var contents: String,
    @ColumnInfo(name = "$SHOUTS_COLUMN_NAME_DATE") var date: String, // ex - Aug 25, 2021 03:08 PM
)
