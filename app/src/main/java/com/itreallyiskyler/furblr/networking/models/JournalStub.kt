package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.nodes.Element

/*
    Example :
    <div class="cell">
        <a href="/journal/10049668/"><strong class="journal_subject">Artist/medical opinion/experience?</strong></a>
        <br class="hideondesktop">
        posted by <a href="/user/blitzdrachin/"><strong>blitzdrachin</strong></a>
        <span title="on Nov 20, 2021 09:22 PM" class="popup_date">a day ago</span>
    </div>
 */
data class JournalStub(
    val journalId : Long,
    val title : String,
    val author : String,
    val date : String
) {
    companion object : IParserElement<JournalStub> {
        override fun parseFromElement(container: Element): JournalStub {
            val links = container.getElementsByTag("a")
            val journalLink = links[0].attributes()["href"]
            val journalLinkParts = journalLink.substring(1, journalLink.length - 1).split('/')
            val journalId : Long = journalLinkParts[1].toLong()

            val titleContainer = container.getElementsByClass("journal_subject")[0]
            val title : String = titleContainer.text()

            val author : String = links[1].child(0).text()

            val dateContainer = container.getElementsByTag("span")[0]
            val dateText = dateContainer.attributes()["title"]
            val trimmedDateText = dateText.substring(dateText.indexOf(" ") + 1)
            val date : String = DateFormatter(trimmedDateText).toYYYYMMDDhhmm()

            return JournalStub(journalId, title, author, date)
        }
    }
}