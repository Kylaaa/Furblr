package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.nodes.Element

/*
    Example :
    <li class="">
        <div class="avatar">
            <a href="/user/velvetlamb/"><img class="avatar" src="//a.furaffinity.net/1636445059/velvetlamb.gif"></a>
        </div>
        <div class="checkbox">
            <input type="checkbox" name="watches[]" value="137700354">
        </div>
        <div class="info">
            <span>VelvetLamb</span> <small><span title="Nov 23rd, 2021 01:14 AM" class="popup_date">a month ago</span></small>
        </div>
    </li>
 */

data class NotificationWatch (
    override val id: Long,
    override val kind: NotificationId,
    override val sourceName: String,
    override val sourcePost: Long?,
    override val date: String
) : INotification {

    companion object : NotificationBase(), IParserElement<NotificationWatch> {
        override fun parseFromElement(container: Element): NotificationWatch {
            val id = parseId(container)
            val kind: NotificationId = NotificationId.Watch
            val sourceName: String = parseSender(container)
            val sourcePost: Long? = null
            val date: String = parseDate(container, 1)
            return NotificationWatch(id, kind, sourceName, sourcePost, date)
        }

        private fun parseSender(element: Element): String {
            val nameTag = element.select("span")!![0]
            return nameTag.text()
        }
    }
}
