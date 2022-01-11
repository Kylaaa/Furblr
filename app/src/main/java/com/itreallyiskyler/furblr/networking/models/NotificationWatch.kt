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

class NotificationWatch (private val element : Element) : NotificationBase(element) {
    override var kind : NotificationId = NotificationId.Watch

    override var sourceName : String = parseSender(element)
    override var sourcePost : Long? = null
    override var date: String = parseDate(element, 1)

    private fun parseSender(element : Element) : String {
        val nameTag = element.select("span")!![0]
        return nameTag.text()
    }
}
