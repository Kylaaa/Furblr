package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.BuildConfig
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class PageUserDetails(
    val username: String,
    val avatarId: Long,
    val nickname: String = "",
    val dateJoined: String = "",
    val description: String = "",

    // stats
    val statsViewsCount: Long = 0,
    val statsSubmissionsCount: Long = 0,
    val statsFavoritesCount: Long = 0,
    val statsCommentsEarned: Long = 0,
    val statsCommentsMade: Long = 0,
    val statsJournalsCount: Long = 0,

    // money
    val acceptingTrades: Boolean = false,
    val acceptingCommissions: Boolean = false,
    val acceptingShinies: Boolean = false,

    // profile
    val species: String? = null,
    val faveMusic: String? = null,
    val faveCinema: String? = null,
    val faveGames: String? = null,
    val faveGamesPlatforms: String? = null,
    val faveAnimals: String? = null,
    val faveWebsite: String? = null,
    val faveFoods: String? = null,
    val faveQuotes: String? = null,
    val faveArtists: String? = null,

    // links
    val userLinks : UserLinks = UserLinks()
) {

    companion object : IParserHttp<PageUserDetails> {
        override fun parseFromHttp(body: String): PageUserDetails {
            val doc: Document = Jsoup.parse(body)
            // TODO : FIGURE OUT WHY PARSING namesContainer is throwing errors
            val username: String = getUsername(doc)
            val nickname: String = "" //namesContainer.child(1).text()
            val dateJoined: String = ""

            val avatarContainer = doc.getElementsByClass("user-nav-avatar")[0]
            val avatarSrc: String = avatarContainer.attr("src")!!
                .substring(BuildConfig.ASSET_URL.length - "https:".length)
            val avatarId: Long = avatarSrc.split("/")[0].toLong()

            val description: String = ""

            return PageUserDetails(
                username = username,
                avatarId = avatarId,
                nickname = nickname,
                dateJoined = dateJoined,
                description = description,
            )
        }

        private fun getUsername(document: Document): String {
            try {
                val namesContainer = document.getElementsByClass("username")[0]
                val usernameContainer = namesContainer.child(0)
                val username = usernameContainer.text().trim().substring(1)
                return username
            } catch (ex: Exception) {
                throw(Exception(
                    "Failed to parse username.got exception ${ex.message} figure out why from this :/n${document.body()}",
                    ex.cause
                ))
            }

            return "UNPARSABLE USERNAME"
        }
    }
}
