package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.AgeRating
import com.itreallyiskyler.furblr.enum.PostCategory
import com.itreallyiskyler.furblr.util.DateFormatter
import okhttp3.internal.toImmutableList
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class PagePostDetails (private val httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody)

    // metadata
    private var metadataContainer : Element = doc.getElementsByClass("submission-id-container")[0]
    val Title : String = parseTitle(metadataContainer)
    val Artist : String = parseArtist(metadataContainer)
    val UploadDate : String = parseUploadDate(metadataContainer)

    // content
    private var postContainer : Element = doc.getElementById("submissionImg")
    val ContentUrl : String = parseContentUrl(postContainer)

    // description
    private var descriptionContainer : Element = doc.getElementsByClass("submission-description")[0]
    val Description : String = descriptionContainer.text()

    // stats
    private var allStatsContainer : Element = doc.getElementsByClass("submission-stats-container")[0]
    val TotalViews : Long = parseViews(allStatsContainer)
    val TotalFavorites : Long = parseFavorites(allStatsContainer)
    val Rating : AgeRating = parseAgeRating(allStatsContainer)

    // favorite
    private var favoriteContainer : Element = doc.getElementsByClass("favorite-nav")[0]
    val FavoriteKey : String = parseFavoriteKey(favoriteContainer)
    val HasFavorited : Boolean = parseHasFavorited(favoriteContainer)

    // other stuff
    // TODO : parse extra information, use Category to identify HOW to parse the rest of the page
    /*val Category : PostCategory = PostCategory.Other
    private var viewKind = PostCategory.getPostKind(Category)
    val Species
    val Gender
    val Theme
    val Size : Pair<Int, Int>*/

    private var allTagContainers : Elements = doc.getElementsByClass("tags")
    val Tags : Array<IPostTag> = parseTags(allTagContainers)
    private var allCommentContainers : Elements = doc.getElementsByClass("comment_container")
    val Comments : Array<IPostComment> = parseComments(allCommentContainers)

    private fun parseTitle(element : Element) : String {
        val titleElement = element.getElementsByClass("submission-title")[0]
        return titleElement.child(0).child(0).text()
    }
    private fun parseArtist(element: Element) : String {
        val strongElements = element.select("strong")
        return strongElements[0].text()
    }
    private fun parseUploadDate(element: Element) : String {
        val strongElements = element.select("strong")
        val dateText = strongElements[1].child(0).attr("title")
        val df = DateFormatter(dateText)
        return df.toYYYYMMDDhhmm()
    }
    private fun parseContentUrl(element: Element) : String {
        val imageSource  = element.attr("src")
        return "https:$imageSource"
    }
    private fun parseViews(element: Element) : Long {
        val viewsElement = element.getElementsByClass("views")[0]
        return viewsElement.child(0).text().toLong()
    }
    private fun parseFavorites(element:Element) : Long {
        val viewsElement = element.getElementsByClass("favorites")[0]
        return viewsElement.child(0).text().toLong()
    }
    private fun parseFavoriteKey(element:Element) : String {
        val buttons = element.getElementsByClass("button")
        val favButtonIndex = if (buttons.size == 5) 0 else 1
        val linkElement = element.child(favButtonIndex)
        val href : String = linkElement.attr("href")
        val parts = href.split("=")
        return parts[1]
    }
    private fun parseHasFavorited(element:Element) : Boolean {
        val linkElement = element.child(0)
        val favoriteLabel : String = linkElement.text()
        val sign = favoriteLabel[0]
        // when the sign is -, it shows that the user has favorited this post
        return sign == '-'
    }
    private fun parseAgeRating(element: Element) : AgeRating {
        val ratingElement = element.getElementsByClass("rating")[0]
        val classes = ratingElement.child(0).classNames()
        return AgeRating.fromClassList(classes);
    }
    private fun parseTags(tagContainers : Elements) : Array<IPostTag> {
        var tags : MutableMap<String, IPostTag> = mutableMapOf()
        tagContainers.forEach { element ->
            run {
                val tag = PostTag(element)
                if (!tags.contains(tag.Content))
                    tags[tag.Content] = tag
            }
        }
        return tags.values.toTypedArray()
    }
    private fun parseComments(commentContainers : Elements) : Array<IPostComment> {
        var comments : MutableList<IPostComment> = mutableListOf()
        commentContainers.forEach { element -> run {
            try {
                val c = PostComment(element)
                comments.add(c)
            } catch (e : Exception)
            {
                println(e)
            }
        } }
        return comments.toTypedArray()
    }
}
