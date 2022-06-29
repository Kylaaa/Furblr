package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class PageJournalDetails (httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody)

    // creator information
    private var creatorContainer : Element = doc.getElementsByClass("username")[0]
    val Artist : String = parseArtist(creatorContainer)

    // header information
    private var headerContainer : Element = doc.getElementsByClass("section-header")[0]
    val Title : String = parseTitle(headerContainer)
    val UploadDate : String = parseUploadDate(headerContainer)

    // contents
    private var journalContainer : Element = doc.getElementsByClass("journal-content")[0]
    val Contents : String = journalContainer.text()

    // other stuff
    private var allCommentContainers : Elements = doc.getElementsByClass("comment_container")
    val Comments : Array<IPostComment> = parseComments(allCommentContainers)


    private fun parseArtist(element: Element) : String {
        val spanElements = element.select("span")
        return spanElements[0].text().substring(1)
    }
    private fun parseTitle(element : Element) : String {
        val titleElement = element.getElementsByClass("journal-title")[0]
        return titleElement.text()
    }
    private fun parseUploadDate(element: Element) : String {
        val spanElements = element.select("span")
        val dateText = spanElements[0].attr("title")
        val df = DateFormatter(dateText)
        return df.toYYYYMMDDhhmm()
    }
    private fun parseComments(commentContainers : Elements) : Array<IPostComment> {
        var comments : MutableList<IPostComment> = mutableListOf()
        commentContainers.forEach { element -> run {
            try {
                val c = PostComment(element)
                comments.add(c)
            } catch (e : Exception)
            {
                println("Failed to parse comments : " + e.toString())
            }
        } }
        return comments.toTypedArray()
    }
}
