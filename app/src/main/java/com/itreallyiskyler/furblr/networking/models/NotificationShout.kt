package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId
import org.jsoup.nodes.Element

/*
    Example:
    <li class="">
        <input type="checkbox" name="shouts[]" value="51820196">
        <a href="/user/muttyblue/">
            <strong>mutty_blue</strong>
        </a> left a shout <span title="on Sep 4, 2021 11:48 PM" class="popup_date">4 months ago</span>
    </li>
 */

data class NotificationShout (
    override val id: Long,
    override val kind: NotificationId,
    override val sourceName: String,
    override val sourcePost: Long?,
    override val date: String
) : INotification {

    companion object : NotificationBase(), IParserElement<NotificationShout> {
        override fun parseFromElement(container: Element): NotificationShout {
            val id = parseId(container)
            val kind: NotificationId = NotificationId.Shout
            val sourceName: String = parseSender(container)
            val sourcePost: Long? = null
            val date: String = parseDate(container)
            return NotificationShout(id, kind, sourceName, sourcePost, date)
        }

        private fun parseSender(element: Element): String {
            val nameTag: Element = element.select("strong")!![0]
            return nameTag.text()
        }
    }
}
