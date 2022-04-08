package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


class PageOthers(private val httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody)

    val Watches : List<INotification> = getWatches(doc.getElementById("messages-watches"))
    val Comments : List<INotification> = getSubmissionComments(doc.getElementById("messages-comments-submission"))
    val Shouts : List<INotification> = getShouts(doc.getElementById("messages-shouts"))
    val Favorites : List<INotification> = getFavorites(doc.getElementById("messages-favorites"))
    val JournalIds : List<JournalStub> = getJournalStubs(doc.getElementById("messages-journals"))

    private fun getWatches(elementData: Element?) : List<INotification> {
        if (elementData == null) {
            return listOf()
        }

        var notes : MutableList<NotificationWatch> = mutableListOf()
        val items = elementData.select("li")
        for (li in items) {
            notes.add(NotificationWatch(li))
        }
        return notes.toList()
    }

    private fun getSubmissionComments(elementData: Element?) : List<INotification> {
        if (elementData == null) {
            return listOf()
        }

        var notes : MutableList<NotificationComment> = mutableListOf()
        val items = elementData.select("li")
        for (li in items) {
            notes.add(NotificationComment(li))
        }
        return notes.toList()
    }

    private fun getShouts(elementData: Element?) : List<INotification> {
        if (elementData == null) {
            return listOf()
        }

        var notes : MutableList<NotificationShout> = mutableListOf()
        val items = elementData.select("li")
        for (li in items) {
            notes.add(NotificationShout(li))
        }
        return notes.toList()
    }

    private fun getFavorites(elementData: Element?) : List<INotification> {
        if (elementData == null) {
            return listOf()
        }

        var notes : MutableList<NotificationFavorite> = mutableListOf()
        val items = elementData.select("li")
        for (li in items) {
            notes.add(NotificationFavorite(li))
        }
        return notes.toList()
    }

    private fun getJournalStubs(elementData: Element?) : List<JournalStub> {
        if (elementData == null) {
            return listOf()
        }

        val cells = elementData.getElementsByTag("li")
        val divs : List<Element> = cells.map { it.child(0).child(1) }
        val stubs : List<JournalStub> = divs.map { JournalStub(it) }

        return stubs
    }
}
