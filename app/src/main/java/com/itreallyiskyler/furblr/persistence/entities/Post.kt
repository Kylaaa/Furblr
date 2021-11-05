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
const val POSTS_COLUMN_NAME_FAVORITE_KEY = "favorite"
const val POSTS_COLUMN_NAME_HAS_FAVORITED_KEY = "hasFavorited"
const val POSTS_COLUMN_NAME_DATE = "date"

// TODO : connect this table to the Users table through foreign keys

// TODO : Decide if there's a better way to store favorites.
//  Should personalized data be associated with posts?

@Entity
data class Post(
    @PrimaryKey @ColumnInfo(name = "$POSTS_COLUMN_NAME_ID") var id: Long, // the id from the view/%d/ url
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_PROFILE_ID") var profileId: String, // the creator's name
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_TITLE") var title : String, // the title of the post
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_DESCRIPTION") var description : String, // the description of the post
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_CONTENT_ID") var contentsId: String, // the source url for the post
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_COUNT_VIEWS") var viewCount : Long, // the number of views
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_COUNT_COMMENTS") var commentCount : Long, // the number of comments
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_COUNT_FAVORITES") var favoriteCount : Long, // the number of favorites
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_RATING") var rating : String, // enum.AgeRating
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_FAVORITE_KEY") var favKey : String, // the key to favorite the post
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_HAS_FAVORITED_KEY") var hasFavorited : Boolean, // has the user
    @ColumnInfo(name = "$POSTS_COLUMN_NAME_DATE") var date: String, // formatted to datetime YYYY-MM-DD HH:MM
)
