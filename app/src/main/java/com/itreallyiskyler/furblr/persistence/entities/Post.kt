package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// A Post includes the details of an uploaded work of art.
// - content Tags exist in a separate entity
// - user Comments exist in a separate entity

const val POSTS_TABLE_NAME = "post"
const val POSTS_COLUMN_NAME_ID = "id"
const val POSTS_COLUMN_NAME_PROFILE_ID = "profileId"
const val POSTS_COLUMN_NAME_TITLE = "title"
const val POSTS_COLUMN_NAME_DESCRIPTION = "description"
const val POSTS_COLUMN_NAME_CONTENT_ID = "contentId"
const val POSTS_COLUMN_NAME_COUNT_VIEWS = "countViews"
const val POSTS_COLUMN_NAME_COUNT_COMMENTS = "countComments"
const val POSTS_COLUMN_NAME_COUNT_FAVORITES = "countFavorites"
const val POSTS_COLUMN_NAME_RATING = "rating"
const val POSTS_COLUMN_NAME_DATE = "date"

// TODO : connect this table to the Users table through foreign keys

@Entity
data class Post(
    @PrimaryKey @ColumnInfo(name = "$POSTS_COLUMN_NAME_ID") var id: Long,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_PROFILE_ID") var profileId: String,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_TITLE") var title : String,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_DESCRIPTION") var description : String,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_CONTENT_ID") var contentsId: String,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_COUNT_VIEWS") var viewCount : Long,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_COUNT_COMMENTS") var commentCount : Long,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_COUNT_FAVORITES") var favoriteCount : Long,
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_RATING") var rating : String, // enum.AgeRating
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_DATE") var date: String, // formatted to datetime YYYY-MM-DD HH:MM
)
