package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.*
import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.IndexOutOfBoundsException
import java.security.InvalidKeyException

class PagePostDetails (private val httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody)

    // metadata
    private var metadataContainer : Element = getMetadataContainer(doc)
    val Title : String = parseTitle(metadataContainer)
    val Artist : String = parseArtist(metadataContainer)
    val UploadDate : String = parseUploadDate(metadataContainer)

    // content
    val ThumbnailUrl : String? = parseThumbnailUrl(doc)

    private var classificationContainer : Element = doc.getElementsByClass("info text")[0]
    val Category : PostCategory = parseCategory(classificationContainer)
    // val Species // DON'T BOTHER PARSING SPECIES, IT SEEMS LIKE A WORSE VERSION OF THE USER TAGS
    val Gender : PostGender = parseGender(classificationContainer)
    val Theme : PostTheme = parseTheme(classificationContainer)
    val Size : Pair<Int, Int> = parseSize(classificationContainer)

    val Kind : PostKind = PostCategory.getPostKind(Category)
    val ContentUrl : String? = parseContentUrl(Kind, doc)

    // description
    private var descriptionContainer : Element = doc.getElementsByClass("submission-description")[0]
    val Description : String = descriptionContainer.text()

    // stats
    private var allStatsContainer : Element = doc.getElementsByClass("submission-stats-container")[0]
    val TotalViews : Long = parseViews(allStatsContainer)
    val TotalFavorites : Long = parseFavorites(allStatsContainer)
    val Rating : AgeRating = parseAgeRating(allStatsContainer)

    // favorite
    private var favoriteContainer : Element = doc.getElementsByClass("favorite-nav")[0].getElementsByClass("button").filter {
            it -> it.attr("href").startsWith("/fav/") || it.attr("href").startsWith("/unfav/")
    }[0]
    val FavoriteKey : String = parseFavoriteKey(favoriteContainer)
    val HasFavorited : Boolean = parseHasFavorited(favoriteContainer)

    // other stuff
    private var allTagContainers : Elements = doc.getElementsByClass("tags")
    val Tags : Array<IPostTag> = parseTags(allTagContainers)
    private var allCommentContainers : Elements = doc.getElementsByClass("comment_container")
    val Comments : Array<IPostComment> = parseComments(allCommentContainers)

    private fun getMetadataContainer(document: Document) : Element {
        val containers = doc.getElementsByClass("submission-id-container")
        if (containers.size != 1) {
            throw IndexOutOfBoundsException("Could not find the metadata container in $httpBody")
        }
        return containers[0]
    }
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
    private fun parseThumbnailUrl(doc: Document) : String? {
        try {
            val element = doc.getElementById("submissionImg")
            val imageSource  = element.attr("src")
            return "https:$imageSource"
        } catch (ex : Exception) {
            println("Failed to parse thumbnail url : " + ex.message)
        }
        return null
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
        val href : String = element.attr("href")
        val parts = href.split("=")
        if (parts.size != 2){
            println("Error waiting to happen with $Title")
        }
        return parts[1]
    }
    private fun parseHasFavorited(element:Element) : Boolean {
        val favoriteLabel : String = element.text()
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
                println("Failed to parse comments in PagePostDetails : " + e.toString())
            }
        } }
        return comments.toTypedArray()
    }
    private fun parseCategory(element : Element) : PostCategory {
        val categoryElement = element.getElementsByClass("category-name")[0]
        return PostCategory.fromString(categoryElement.text())
    }
    private fun parseGender(element : Element) : PostGender {
        val genderElement = element.child(2).child(1)
        return PostGender.fromString(genderElement.text())
    }
    private fun parseTheme(element : Element) : PostTheme {
        val themeElement = element.getElementsByClass("type-name")[0]
        return PostTheme.fromString(themeElement.text())
    }
    private fun parseSize(element : Element) : Pair<Int, Int> {
        val sizeContainer = element.children()[3]
        val sizeSpan = sizeContainer.children()[1]
        val sizes = sizeSpan.text().split(" x ")
        return Pair<Int, Int>(sizes[0].toInt(), sizes[1].toInt())
    }
    private fun parseContentUrl(postKind : PostKind, document : Document) : String? {
        var contentUrl : String? = null
        when (postKind.id) {
            PostKind.Downloadable.id -> {
                // TODO - parse when we have an example
                println("Unknown format")
            }
            PostKind.Flash.id -> {
                val flashContainer : Element = doc.getElementById("flash_embed")
                contentUrl = "https:" + flashContainer.attr("data")
            }
            PostKind.Image.id -> {
                // the image _is_ the content
            }
            PostKind.Journal.id -> {
                throw InvalidKeyException("Journals shouldn't be appearing on this page.")
            }
            PostKind.Music.id -> {
                try {
                    val audioContainer: Element =
                        doc.getElementsByClass("audio-player")[0] //-container
                    contentUrl = "https:" + audioContainer.attr("src")
                }
                catch(ex : Exception){
                    println("Could not find an audio player link. " + ex.message)
                }
            }
            PostKind.Writing.id -> {
                // there's no guarantee that there's a download link
                try {
                    val textContainer: Elements = doc.getElementsByClass("submission-writing")
                    val downloadContainer : Element = textContainer[0].select("a")[0]
                    contentUrl = "https:" + downloadContainer.attr("href")
                }
                catch (ex : Exception)
                {
                    println("No download link to parse from writing page." + ex.message)
                }
            }
            PostKind.Unknown.id -> {
                println("Unknown post kind")
            }
        }

        return contentUrl
    }
}
