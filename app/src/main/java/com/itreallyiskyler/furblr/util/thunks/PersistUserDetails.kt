package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageUserDetails
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistUserDetails(userDetails : PageUserDetails) {
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
        userDetails.userLinks.siteHome,
        userDetails.userLinks.siteBlizzardBattlenet,
        userDetails.userLinks.siteDealersDen,
        userDetails.userLinks.siteDeviantArt,
        userDetails.userLinks.siteDiscord,
        userDetails.userLinks.siteFacebook,
        userDetails.userLinks.siteFurbuy,
        userDetails.userLinks.siteFurryNetwork,
        userDetails.userLinks.siteImvu,
        userDetails.userLinks.siteInkBunny,
        userDetails.userLinks.siteKoFi,
        userDetails.userLinks.siteMicrosoftXboxLive,
        userDetails.userLinks.siteNintendo3ds,
        userDetails.userLinks.siteNintendoWiiU,
        userDetails.userLinks.siteNintendoSwitch,
        userDetails.userLinks.sitePatreon,
        userDetails.userLinks.sitePicarto,
        userDetails.userLinks.siteSecondLife,
        userDetails.userLinks.siteSkype,
        userDetails.userLinks.siteSoFurry,
        userDetails.userLinks.siteSonyPSN,
        userDetails.userLinks.siteSteam,
        userDetails.userLinks.siteTelegram,
        userDetails.userLinks.siteTransfur,
        userDetails.userLinks.siteTumblr,
        userDetails.userLinks.siteTwitchTv,
        userDetails.userLinks.siteTwitter,
        userDetails.userLinks.siteWeasyl,
        userDetails.userLinks.siteYoutube
    )

    SingletonManager.get().DBManager.getDB().usersDao().insertOrUpdateUsers(user)
}