package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val VIEW_COLUMN_NAME_ID = "id"
const val VIEW_COLUMN_NAME_PROFILE_ID = "profileId"
const val VIEW_COLUMN_NAME_TITLE = "title"
const val VIEW_COLUMN_NAME_DESCRIPTION = "description"
const val VIEW_COLUMN_NAME_RATING = "rating"
const val VIEW_COLUMN_NAME_DATE = "date"
const val VIEW_COLUMN_NAME_CONTENT_URL = "contentUrl"
const val VIEW_COLUMN_NAME_SUBMISSION_IMAGE = "submissionImageUrl"
const val VIEW_COLUMN_NAME_SUBMISSION_IMAGE_SIZE_WIDTH = "submissionImageSizeWidth"
const val VIEW_COLUMN_NAME_SUBMISSION_IMAGE_SIZE_HEIGHT = "submissionImageSizeHeight"
const val VIEW_COLUMN_NAME_COUNT_VIEWS = "countViews"
const val VIEW_COLUMN_NAME_COUNT_COMMENTS = "countComments"
const val VIEW_COLUMN_NAME_COUNT_FAVORITES = "countFavorites"
const val VIEW_COLUMN_NAME_FAVORITE_KEY = "favorite"
const val VIEW_COLUMN_NAME_HAS_FAVORITED_KEY = "hasFavorited"
const val VIEW_COLUMN_NAME_KIND = "kind"
const val VIEW_COLUMN_NAME_CATEGORY = "category"
const val VIEW_COLUMN_NAME_THEME = "theme"
const val VIEW_COLUMN_NAME_GENDER = "gender"


// TODO : connect this table to the Users table through foreign keys
// TODO : Decide if there's a better way to store favorites.
//  Should personalized data be associated with posts?

const val VIEW_TABLE_NAME = "view"

@Entity
data class View(
    @PrimaryKey @ColumnInfo(name = VIEW_COLUMN_NAME_ID) var id: Long,

    // Post Content
    @ColumnInfo(name = VIEW_COLUMN_NAME_PROFILE_ID) var profileId: String,
    @ColumnInfo(name = VIEW_COLUMN_NAME_TITLE) var title : String,
    @ColumnInfo(name = VIEW_COLUMN_NAME_DESCRIPTION) var description : String,
    @ColumnInfo(name = VIEW_COLUMN_NAME_CONTENT_URL) var contentUrl : String?,

    // Submission Image Data
    @ColumnInfo(name = VIEW_COLUMN_NAME_SUBMISSION_IMAGE) var submissionImgUrl: String?,
    @ColumnInfo(name = VIEW_COLUMN_NAME_SUBMISSION_IMAGE_SIZE_WIDTH) var submissionImgSizeWidth : Int,
    @ColumnInfo(name = VIEW_COLUMN_NAME_SUBMISSION_IMAGE_SIZE_HEIGHT) var submissionImgSizeHeight : Int,

    // Counts
    @ColumnInfo(name = VIEW_COLUMN_NAME_COUNT_VIEWS) var viewCount : Long,
    @ColumnInfo(name = VIEW_COLUMN_NAME_COUNT_COMMENTS) var commentCount : Long,
    @ColumnInfo(name = VIEW_COLUMN_NAME_COUNT_FAVORITES) var favoriteCount : Long,

    // Favoriting
    @ColumnInfo(name = VIEW_COLUMN_NAME_FAVORITE_KEY) var favKey : String,
    @ColumnInfo(name = VIEW_COLUMN_NAME_HAS_FAVORITED_KEY) var hasFavorited : Boolean,

    // Post Metadata
    @ColumnInfo(name = VIEW_COLUMN_NAME_DATE) var date: String,
    @ColumnInfo(name = VIEW_COLUMN_NAME_RATING) var rating : String,
    @ColumnInfo(name = VIEW_COLUMN_NAME_KIND) var kind: Int,
    @ColumnInfo(name = VIEW_COLUMN_NAME_CATEGORY) val category : Int,
    @ColumnInfo(name = VIEW_COLUMN_NAME_THEME) val theme : Int,
    @ColumnInfo(name = VIEW_COLUMN_NAME_GENDER) val gender : Int
)
