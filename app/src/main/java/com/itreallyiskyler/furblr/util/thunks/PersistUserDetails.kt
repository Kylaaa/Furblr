package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.networking.models.PageUserDetails
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistUserDetails(dbImpl: AppDatabase,
                        userDetails : PageUserDetails) {
    val user = User(
        userDetails.username,
        userDetails.nickname,
        userDetails.avatarId,
        userDetails.description,
        userDetails.dateJoined,

        // stats
        userDetails.statsViewsCount,
        userDetails.statsSubmissionsCount,
        userDetails.statsFavoritesCount,
        userDetails.statsCommentsEarned,
        userDetails.statsCommentsMade,
        userDetails.statsJournalsCount,

        // money
        userDetails.acceptingTrades,
        userDetails.acceptingCommissions,
        userDetails.acceptingShinies,

        // profile
        userDetails.species,
        userDetails.faveMusic,
        userDetails.faveCinema,
        userDetails.faveGames,
        userDetails.faveGamesPlatforms,
        userDetails.faveAnimals,
        userDetails.faveWebsite,
        userDetails.faveFoods,
        userDetails.faveQuotes,
        userDetails.faveArtists,

        // links
        userDetails.siteHome,
        userDetails.siteBlizzardBattlenet,
        userDetails.siteDealersDen,
        userDetails.siteDeviantArt,
        userDetails.siteDiscord,
        userDetails.siteFacebook,
        userDetails.siteFurbuy,
        userDetails.siteFurryNetwork,
        userDetails.siteImvu,
        userDetails.siteInkBunny,
        userDetails.siteKoFi,
        userDetails.siteMicrosoftXboxLive,
        userDetails.siteNintendo3ds,
        userDetails.siteNintendoWiiU,
        userDetails.siteNintendoSwitch,
        userDetails.sitePatreon,
        userDetails.sitePicarto,
        userDetails.siteSecondLife,
        userDetails.siteSkype,
        userDetails.siteSoFurry,
        userDetails.siteSonyPSN,
        userDetails.siteSteam,
        userDetails.siteTelegram,
        userDetails.siteTransfur,
        userDetails.siteTumblr,
        userDetails.siteTwitchTv,
        userDetails.siteTwitter,
        userDetails.siteWeasyl,
        userDetails.siteYoutube
    )

    dbImpl.usersDao().insertOrUpdateUsers(user)
}