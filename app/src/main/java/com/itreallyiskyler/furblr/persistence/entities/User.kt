package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// A User includes the details from a specific user's profile
// - profile Shouts exist in a separate entity
// - user Comments exist in a separate entity

const val USERS_TABLE_NAME = "user"
const val USERS_COLUMN_NAME_ID = "username"
const val USERS_COLUMN_NAME_NICKNAME = "nickname"
const val USERS_COLUMN_NAME_AVATAR_ID = "avatarId"
const val USERS_COLUMN_NAME_DESCRIPTION = "description"
const val USERS_COLUMN_NAME_DATE_JOINED = "dateJoined"
const val USERS_COLUMN_NAME_STATS_VIEW_COUNT = "statsViewCount"
const val USERS_COLUMN_NAME_STATS_SUBMISSIONS_COUNT = "statsSubmissionsCount"
const val USERS_COLUMN_NAME_STATS_FAVORITES_COUNT = "statsFavoritesCount"
const val USERS_COLUMN_NAME_STATS_COMMENTS_EARNED_COUNT = "statsCommentsEarnedCount"
const val USERS_COLUMN_NAME_STATS_COMMENTS_MADE_COUNT = "statsCommentsMadeCount"
const val USERS_COLUMN_NAME_STATS_JOURNALS_COUNT = "statsJournalsCount"
const val USERS_COLUMN_NAME_ACCEPTING_TRADES = "acceptingTrades"
const val USERS_COLUMN_NAME_ACCEPTING_COMMISSIONS = "acceptingCommissions"
const val USERS_COLUMN_NAME_ACCEPTING_SHINIES = "acceptingShinies"
const val USERS_COLUMN_NAME_SPECIES = "species"
const val USERS_COLUMN_NAME_FAVE_MUSIC = "faveMusic"
const val USERS_COLUMN_NAME_FAVE_CINEMA = "faveCinema"
const val USERS_COLUMN_NAME_FAVE_GAMES = "faveGames"
const val USERS_COLUMN_NAME_FAVE_GAMES_PLATFORMS = "faveGamePlatforms"
const val USERS_COLUMN_NAME_FAVE_ANIMALS = "faveAnimals"
const val USERS_COLUMN_NAME_FAVE_WEBSITE = "faveWebsite"
const val USERS_COLUMN_NAME_FAVE_FOODS = "faveFoods"
const val USERS_COLUMN_NAME_FAVE_QUOTES = "faveQuotes"
const val USERS_COLUMN_NAME_FAVE_ARTISTS = "faveArtists"

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name = "$USERS_COLUMN_NAME_ID") var username: String,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_NICKNAME") var nickname : String,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_AVATAR_ID") var avatarId : Long,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_DESCRIPTION") var description : String,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_DATE_JOINED") var dateJoined : String,

    // stats
    @ColumnInfo(name = "$USERS_COLUMN_NAME_STATS_VIEW_COUNT") var statsViewsCount : Long = 0,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_STATS_SUBMISSIONS_COUNT") var statsSubmissionsCount : Long = 0,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_STATS_FAVORITES_COUNT") var statsFavoritesCount : Long = 0,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_STATS_COMMENTS_EARNED_COUNT") var statsCommentsEarned : Long = 0,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_STATS_COMMENTS_MADE_COUNT") var statsCommentsMade : Long = 0,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_STATS_JOURNALS_COUNT") var statsJournalsCount : Long = 0,

    // money
    @ColumnInfo(name = "$USERS_COLUMN_NAME_ACCEPTING_TRADES") var acceptingTrades : Boolean = false,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_ACCEPTING_COMMISSIONS") var acceptingCommissions : Boolean = false,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_ACCEPTING_SHINIES") var acceptingShinies : Boolean = false,

    // profile
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SPECIES") var species : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_MUSIC") var faveMusic : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_CINEMA") var faveCinema : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_GAMES") var faveGames : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_GAMES_PLATFORMS") var faveGamesPlatforms : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_ANIMALS") var faveAnimals : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_WEBSITE") var faveWebsite : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_FOODS") var faveFoods : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_QUOTES") var faveQuotes : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_FAVE_ARTISTS") var faveArtists : String? = "",
)
