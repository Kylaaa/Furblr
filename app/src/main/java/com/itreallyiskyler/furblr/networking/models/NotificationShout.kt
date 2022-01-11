package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.util.DateFormatter
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

class NotificationShout (private val element : Element) : NotificationBase(element) {
    override var kind : NotificationId = NotificationId.Shout

    override var sourceName : String = parseSender(element)
    override var sourcePost : Long? = null
    override var date : String = parseDate(element)

    private fun parseSender(element: Element) : String {
        val nameTag : Element = element.select("strong")!![0]
        return nameTag.text()
    }
}
