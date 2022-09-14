package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.*
import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.IndexOutOfBoundsException
import java.security.InvalidKeyException

data class PagePostDetails (
    val title : String,
    val artist : String,
    val uploadDate : String,
    val thumbnailUrl : String?,
    val category: PostCategory,
    val gender: PostGender,
    val theme: PostTheme,
    val size: Pair<Int, Int>,
    val kind: PostKind,
    val contentUrl: String?,
    val description: String,
    val totalViews: Long,
    val totalFavorites: Long,
    val rating: AgeRating,
    val favoriteKey: String,
    val hasFavorited: Boolean,
    val tags: List<IPostTag>,
    val comments: List<IPostComment>
) {

    companion object : IParserHttp<PagePostDetails> {
        override fun parseFromHttp(body: String): PagePostDetails {
            val doc: Document = Jsoup.parse(body)

            // metadata
            val metadataContainer: Element = getMetadataContainer(doc)
            val title: String = parseTitle(metadataContainer)
            val artist: String = parseArtist(metadataContainer)
            val uploadDate: String = parseUploadDate(metadataContainer)

            // content
            val thumbnailUrl: String? = parseThumbnailUrl(doc)

            val classificationContainer: Element = doc.getElementsByClass("info text")[0]
            val category: PostCategory = parseCategory(classificationContainer)
            // val Species // DON'T BOTHER PARSING SPECIES, IT SEEMS LIKE A WORSE VERSION OF THE USER TAGS
            val gender: PostGender = parseGender(classificationContainer)
            val theme: PostTheme = parseTheme(classificationContainer)
            val size: Pair<Int, Int> = parseSize(classificationContainer)

            val kind: PostKind = PostCategory.getPostKind(category)
            val contentUrl: String? = parseContentUrl(kind, doc)

            // description
            val descriptionContainer: Element =
                doc.getElementsByClass("submission-description")[0]
            val description: String = descriptionContainer.text()

            // stats
            val allStatsContainer: Element =
                doc.getElementsByClass("submission-stats-container")[0]
            val totalViews: Long = parseViews(allStatsContainer)
            val totalFavorites: Long = parseFavorites(allStatsContainer)
            val rating: AgeRating = parseAgeRating(allStatsContainer)

            // favorite
            val favoriteContainer: Element =
                doc.getElementsByClass("favorite-nav")[0].getElementsByClass("button")
                    .filter { it ->
                        it.attr("href").startsWith("/fav/") || it.attr("href").startsWith("/unfav/")
                    }[0]
            val favoriteKey: String = parseFavoriteKey(favoriteContainer, title)
            val hasFavorited: Boolean = parseHasFavorited(favoriteContainer)

            // other stuff
            val allTagContainers: Elements = doc.getElementsByClass("tags")
            val tags: List<IPostTag> = parseTags(allTagContainers)
            val allCommentContainers: Elements = doc.getElementsByClass("comment_container")
            val comments: List<IPostComment> = parseComments(allCommentContainers)

            return PagePostDetails(
                title,
                artist,
                uploadDate,
                thumbnailUrl,
                category,
                gender,
                theme,
                size,
                kind,
                contentUrl,
                description,
                totalViews,
                totalFavorites,
                rating,
                favoriteKey,
                hasFavorited,
                tags,
                comments
            )
        }

        private fun getMetadataContainer(document: Document): Element {
            val containers = document.getElementsByClass("submission-id-container")
            if (containers.size != 1) {
                throw IndexOutOfBoundsException("Could not find the metadata container in ${document.body()}")
            }
            return containers[0]
        }

        private fun parseTitle(element: Element): String {
            val titleElement = element.getElementsByClass("submission-title")[0]
            return titleElement.child(0).child(0).text()
        }

        private fun parseArtist(element: Element): String {
            val strongElements = element.select("strong")
            return strongElements[0].text()
        }

        private fun parseUploadDate(element: Element): String {
            val strongElements = element.select("strong")
            val dateText = strongElements[1].child(0).attr("title")
            val df = DateFormatter(dateText)
            return df.toYYYYMMDDhhmm()
        }

        private fun parseThumbnailUrl(doc: Document): String? {
            try {
                val element = doc.getElementById("submissionImg")
                val imageSource = element.attr("src")
                return "https:$imageSource"
            } catch (ex: Exception) {
                println("Failed to parse thumbnail url : " + ex.message)
            }
            return null
        }

        private fun parseViews(element: Element): Long {
            val viewsElement = element.getElementsByClass("views")[0]
            return viewsElement.child(0).text().toLong()
        }

        private fun parseFavorites(element: Element): Long {
            val viewsElement = element.getElementsByClass("favorites")[0]
            return viewsElement.child(0).text().toLong()
        }

        private fun parseFavoriteKey(element: Element, title: String): String {
            val href: String = element.attr("href")
            val parts = href.split("=")
            if (parts.size != 2) {
                println("Error waiting to happen with $title")
            }
            return parts[1]
        }

        private fun parseHasFavorited(element: Element): Boolean {
            val favoriteLabel: String = element.text()
            val sign = favoriteLabel[0]
            // when the sign is -, it shows that the user has favorited this post
            return sign == '-'
        }

        private fun parseAgeRating(element: Element): AgeRating {
            val ratingElement = element.getElementsByClass("rating")[0]
            val classes = ratingElement.child(0).classNames()
            return AgeRating.fromClassList(classes);
        }

        private fun parseTags(tagContainers: Elements): List<IPostTag> {
            val tags = tagContainers.map { element ->
                PostTag.parseFromElement(element)
            }.toSet()
            return tags.toList()
        }

        private fun parseComments(commentContainers: Elements): List<IPostComment> {
            // TODO : figure out why parsing comments breaks
            return commentContainers.mapNotNull { element ->
                PostComment.parseFromElement(element)
            }
        }

        private fun parseCategory(element: Element): PostCategory {
            val categoryElement = element.getElementsByClass("category-name")[0]
            return PostCategory.fromString(categoryElement.text())
        }

        private fun parseGender(element: Element): PostGender {
            val genderElement = element.child(2).child(1)
            return PostGender.fromString(genderElement.text())
        }

        private fun parseTheme(element: Element): PostTheme {
            val themeElement = element.getElementsByClass("type-name")[0]
            return PostTheme.fromString(themeElement.text())
        }

        private fun parseSize(element: Element): Pair<Int, Int> {
            val sizeContainer = element.children()[3]
            val sizeSpan = sizeContainer.children()[1]
            val sizes = sizeSpan.text().split(" x ")
            return Pair<Int, Int>(sizes[0].toInt(), sizes[1].toInt())
        }

        private fun parseContentUrl(postKind: PostKind, document: Document): String? {
            var contentUrl: String? = null
            when (postKind.id) {
                PostKind.Downloadable.id -> {
                    // TODO - parse when we have an example
                    println("Unknown format")
                }
                PostKind.Flash.id -> {
                    val flashContainer: Element = document.getElementById("flash_embed")
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
                            document.getElementsByClass("audio-player")[0] //-container
                        contentUrl = "https:" + audioContainer.attr("src")
                    } catch (ex: Exception) {
                        println("Could not find an audio player link. " + ex.message)
                    }
                }
                PostKind.Writing.id -> {
                    // there's no guarantee that there's a download link
                    try {
                        val textContainer: Elements = document.getElementsByClass("submission-writing")
                        val downloadContainer: Element = textContainer[0].select("a")[0]
                        contentUrl = "https:" + downloadContainer.attr("href")
                    } catch (ex: Exception) {
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
}

