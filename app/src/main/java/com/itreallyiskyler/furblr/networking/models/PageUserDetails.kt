package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.BuildConfig
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class PageUserDetails (private val httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody);

    private var headerContainer = doc.getElementById("user-profile")
    private var namesContainer = headerContainer.getElementsByClass("username")[0]
    private var usernameContainer = namesContainer.child(0)
    val username: String = usernameContainer.getElementsByTag("span")[0].text().trim().substring(1)
    val nickname : String = "" //namesContainer.child(1).text()
    val dateJoined : String = ""

    private var avatarContainer = headerContainer.getElementsByClass("user-nav-avatar")[0]
    private var avatarSrc :String = avatarContainer.attr("src")!!.substring(BuildConfig.ASSET_URL.length - "https:".length)
    val avatarId : Long = avatarSrc.split("/")[0].toLong()

    val description : String = ""


    // stats
    val statsViewsCount : Long = 0
    val statsSubmissionsCount : Long = 0
    val statsFavoritesCount : Long = 0
    val statsCommentsEarned : Long = 0
    val statsCommentsMade : Long = 0
    val statsJournalsCount : Long = 0

    // money
    val acceptingTrades : Boolean = false
    val acceptingCommissions : Boolean = false
    val acceptingShinies : Boolean = false

    // profile
    val species : String? = null
    val faveMusic : String? = null
    val faveCinema : String? = null
    val faveGames : String? = null
    val faveGamesPlatforms : String? = null
    val faveAnimals : String? = null
    val faveWebsite : String? = null
    val faveFoods : String? = null
    val faveQuotes : String? = null
    val faveArtists : String? = null

    // links
    val siteHome : String? = null
    val siteBlizzardBattlenet : String? = null
    val siteDealersDen : String? = null
    val siteDeviantArt : String? = null
    val siteDiscord : String? = null
    val siteFacebook : String? = null
    val siteFurbuy : String? = null
    val siteFurryNetwork : String? = null
    val siteImvu : String? = null
    val siteInkBunny : String? = null
    val siteKoFi : String? = null
    val siteMicrosoftXboxLive : String? = null
    val siteNintendo3ds : String? = null
    val siteNintendoWiiU : String? = null
    val siteNintendoSwitch : String? = null
    val sitePatreon : String? = null
    val sitePicarto : String? = null
    val siteSecondLife : String? = null
    val siteSkype : String? = null
    val siteSoFurry : String? = null
    val siteSonyPSN : String? = null
    val siteSteam : String? = null
    val siteTelegram : String? = null
    val siteTransfur : String? = null
    val siteTumblr : String? = null
    val siteTwitchTv : String? = null
    val siteTwitter : String? = null
    val siteWeasyl : String? = null
    val siteYoutube : String? = null
}
