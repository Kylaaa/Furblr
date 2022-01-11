package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.nodes.Element

// This is the base class for parsing notifications
abstract class NotificationBase (private val element : Element) : INotification {
    override var id: Long = parseId(element)

    private fun parseId(element : Element) : Long {
        val checkboxElement : Element = element.select("input")!![0]
        return checkboxElement.attr("value")!!.toLong()
    }
    protected fun parseDate(element : Element, index : Int = 0, stripPrefix : Boolean = false) : String {
        val dateTag : Element = element.select("span")!![index]
        val longDate : String = dateTag.attr("title")!!
        return DateFormatter(longDate).toYYYYMMDDhhmm()
    }
}
