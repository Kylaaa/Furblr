package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val JOURNALS_TABLE_NAME = "journal"
const val JOURNALS_COLUMN_NAME_ID = "id"
const val JOURNALS_COLUMN_NAME_PROFILE_ID = "profileId"
const val JOURNALS_COLUMN_NAME_TITLE = "title"
const val JOURNALS_COLUMN_NAME_MESSAGE = "message"
const val JOURNALS_COLUMN_NAME_DATE = "date"


@Entity
data class Journal(
    @PrimaryKey @ColumnInfo(name = "$JOURNALS_COLUMN_NAME_ID") var id: Long, // the id from the journal/%d/ url
    @ColumnInfo(name = "$JOURNALS_COLUMN_NAME_PROFILE_ID") var profileId: String, // the creator's name
    @ColumnInfo(name = "$JOURNALS_COLUMN_NAME_TITLE") var title : String, // the title of the post
    @ColumnInfo(name = "$JOURNALS_COLUMN_NAME_MESSAGE") var message : String, // the contents of the post
    @ColumnInfo(name = "$JOURNALS_COLUMN_NAME_DATE") var date: String, // formatted to datetime YYYY-MM-DD HH:MM
)
