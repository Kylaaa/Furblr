package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.nodes.Element

/*
    Example :
    <li class="">
        <input type="checkbox" name="comments-submissions[]" value="155461822">
        <a href="/user/carrot/">
            <strong>Carrot</strong>
        </a> replied to your comment on <em>their</em> <strong>
            "<a href="/view/41552462/#cid:155461822">
                Duchess and Marie
            </a>"
        </strong>
        <span title="on Apr 21, 2021 06:32 PM" class="popup_date">8 months ago</span>
    </li>
 */

class NotificationComment (private val element : Element) : NotificationBase(element) {
    override var kind : NotificationId = parseKind(element)

    override var sourceName : String = parseSender(element)
    override var sourcePost : Long? = parseSource(element)
    override var date : String = parseDate(element)

    private fun parseKind(element: Element) : NotificationId {
        return if (element.select("em").size > 0)
            NotificationId.SubmissionCommentReply
        else
            NotificationId.SubmissionComment
    }
    private fun parseSender(element : Element) : String {
        val nameTag : Element = element.select("strong")!![0]
        return nameTag.text()
    }
    private fun parseSource(element : Element) : Long {
        val postTag : Element = element.select("a")!![1]
        val link : String = postTag.attr("href")!!.toString()
        val parts = link.split("/")
        return parts[2].toLong()
    }
}
