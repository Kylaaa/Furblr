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
const val USERS_COLUMN_NAME_SITE_HOME = "siteHome"
const val USERS_COLUMN_NAME_SITE_BLIZZARD_BATTLENET = "siteBlizzardBattlenet"
const val USERS_COLUMN_NAME_SITE_DEALERSDEN = "siteDealersDen"
const val USERS_COLUMN_NAME_SITE_DEVIANTART = "siteDeviantart"
const val USERS_COLUMN_NAME_SITE_DISCORD = "siteDiscord"
const val USERS_COLUMN_NAME_SITE_FACEBOOK = "siteFacebook"
const val USERS_COLUMN_NAME_SITE_FURBUY = "siteFurbuy"
const val USERS_COLUMN_NAME_SITE_FURRYNETWORK = "siteFurryNetwork"
const val USERS_COLUMN_NAME_SITE_IMVU = "siteImvu"
const val USERS_COLUMN_NAME_SITE_INKBUNNY = "siteInkBunny"
const val USERS_COLUMN_NAME_SITE_KOFI = "siteKoFi"
const val USERS_COLUMN_NAME_SITE_MICROSOFT_XBOXLIVE = "siteMicrosoftXBoxLive"
const val USERS_COLUMN_NAME_SITE_NINTENDO_3DS = "siteNintendo3DS"
const val USERS_COLUMN_NAME_SITE_NINTENDO_SWITCH = "siteNintendoSwitch"
const val USERS_COLUMN_NAME_SITE_NINTENDO_WIIU = "siteNintendoWiiU"
const val USERS_COLUMN_NAME_SITE_PATREON = "sitePatreon"
const val USERS_COLUMN_NAME_SITE_PICARTO = "sitePicarto"
const val USERS_COLUMN_NAME_SITE_SECONDLIFE = "siteSecondLife"
const val USERS_COLUMN_NAME_SITE_SKYPE = "siteSkype"
const val USERS_COLUMN_NAME_SITE_SOFURRY = "siteSoFurry"
const val USERS_COLUMN_NAME_SITE_SONY_PSN = "siteSonyPSN"
const val USERS_COLUMN_NAME_SITE_STEAM = "siteSteam"
const val USERS_COLUMN_NAME_SITE_TELEGRAM = "siteTelegram"
const val USERS_COLUMN_NAME_SITE_TRANSFUR = "siteTransfur"
const val USERS_COLUMN_NAME_SITE_TUMBLR = "siteTumblr"
const val USERS_COLUMN_NAME_SITE_TWITCHTV = "siteTwitchTV"
const val USERS_COLUMN_NAME_SITE_TWITTER = "siteTwitter"
const val USERS_COLUMN_NAME_SITE_WEASYL = "siteWeasyl"
const val USERS_COLUMN_NAME_SITE_YOUTUBE = "siteYouTube"

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

    // links
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_HOME") var siteHome : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_BLIZZARD_BATTLENET") var siteBlizzardBattlenet : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_DEALERSDEN") var siteDealersDen : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_DEVIANTART") var siteDeviantArt : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_DISCORD") var siteDiscord : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_FACEBOOK") var siteFacebook : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_FURBUY") var siteFurbuy : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_FURRYNETWORK") var siteFurryNetwork : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_IMVU") var siteImvu : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_INKBUNNY") var siteInkBunny : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_KOFI") var siteKoFi : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_MICROSOFT_XBOXLIVE") var siteMicrosoftXboxLive : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_NINTENDO_3DS") var siteNintendo3ds : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_NINTENDO_WIIU") var siteNintendoWiiU : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_NINTENDO_SWITCH") var siteNintendoSwitch : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_PATREON") var sitePatreon : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_PICARTO") var sitePicarto : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_SECONDLIFE") var siteSecondLife : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_SKYPE") var siteSkype : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_SOFURRY") var siteSoFurry : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_SONY_PSN") var siteSonyPSN : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_STEAM") var siteSteam : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_TELEGRAM") var siteTelegram : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_TRANSFUR") var siteTransfur : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_TUMBLR") var siteTumblr : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_TWITCHTV") var siteTwitchTv : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_TWITTER") var siteTwitter : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_WEASYL") var siteWeasyl : String? = "",
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SITE_YOUTUBE") var siteYoutube : String? = "",
)
