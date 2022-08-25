package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class PageJournalDetails (
    val artist : String,
    val title : String,
    val uploadDate : String,
    val contents : String,
    val comments : List<IPostComment>
) {
    companion object : IParserHttp<PageJournalDetails> {
        override fun parseFromHttp(body: String): PageJournalDetails {
            val doc: Document = Jsoup.parse(body)

            // creator information
            val creatorContainer: Element = doc.getElementsByClass("username")[0]
            val artist: String = parseArtist(creatorContainer)

            // header information
            val headerContainer: Element = doc.getElementsByClass("section-header")[0]
            val title: String = parseTitle(headerContainer)
            val uploadDate: String = parseUploadDate(headerContainer)

            // contents
            val journalContainer: Element = doc.getElementsByClass("journal-content")[0]
            val contents: String = journalContainer.text()

            // other stuff
            val allCommentContainers: Elements = doc.getElementsByClass("comment_container")
            val comments: List<IPostComment> = parseComments(allCommentContainers)

            return PageJournalDetails(artist, title, uploadDate, contents, comments)
        }

        private fun parseArtist(element: Element): String {
            val spanElements = element.select("h2")
            return spanElements[0].text().substring(1)
        }

        private fun parseTitle(element: Element): String {
            val titleElement = element.getElementsByClass("journal-title")[0]
            return titleElement.text()
        }

        private fun parseUploadDate(element: Element): String {
            val spanElements = element.select("span")
            val dateText = spanElements[0].attr("title")
            val df = DateFormatter(dateText)
            return df.toYYYYMMDDhhmm()
        }

        private fun parseComments(commentContainers: Elements): List<IPostComment> {
            return commentContainers.map { element ->
                PostComment.parseFromElement(element)
            }
        }
    }
}
