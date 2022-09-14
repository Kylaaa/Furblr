package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.nodes.Element
import java.util.*

/*
    Example :
    <li class="">
        <input type="checkbox" name="favorites[]" value="1173038567">
        <a href="/user/kunyka/">
            <strong>kunYKA</strong>
        </a> favorited <a href="/view/44390879/">
            <strong>"Furblr - small facelift"</strong>
        </a>
        <span title="Oct 30th, 2021 04:49 AM" class="popup_date">2 months ago</span>
    </li>

    or

    <li>
        <input type="checkbox" name="favorites[]" value="1169562033" checked="checked">
        The favorite this notification was for has since been removed by the user.
    </li>
 */


data class NotificationFavorite(
    override val id: Long,
    override val kind: NotificationId,
    override val sourceName: String,
    override val sourcePost: Long?,
    override val date: String
) : INotification {

    companion object : NotificationBase(), IParserElement<NotificationFavorite> {

        override fun parseFromElement(container: Element): NotificationFavorite {
            val id = parseId(container)
            val kind: NotificationId = NotificationId.Favorite
            val sourceName: String = parseSender(container)
            val sourcePost: Long? = parseSource(container)
            val date: String = parseDate(container)
            return NotificationFavorite(id, kind, sourceName, sourcePost, date)
        }
        private fun parseSender(element: Element): String {
            val nameTag: Element = element.select("strong")!![0]
            return nameTag.text()
        }

        private fun parseSource(element: Element): Long? {
            val linkTag: Element = element.select("a")!![1]
            val link: String = linkTag.attr("href")!!.toString()
            val parts = link.split("/")
            return parts[2].toLong()
        }
    }
}
