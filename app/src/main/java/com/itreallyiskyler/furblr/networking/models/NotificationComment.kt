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

data class NotificationComment(
    override val id: Long,
    override val kind: NotificationId,
    override val sourceName: String,
    override val sourcePost: Long?,
    override val date: String
) : INotification {


    companion object : NotificationBase(), IParserElement<NotificationComment> {
        override fun parseFromElement(container: Element): NotificationComment {
            val id = parseId(container)
            val kind : NotificationId = parseKind(container)

            val sourceName : String = parseSender(container)
            val sourcePost : Long? = parseSource(container)
            val date : String = parseDate(container)

            return NotificationComment(id, kind, sourceName, sourcePost, date)
        }

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
        private fun parseSource(element : Element) : Long? {
            val postTag : Element = element.select("a")!![1]
            val link : String = postTag.attr("href")!!.toString()
            val parts = link.split("/")
            return parts[2].toLong()
        }
    }
}
