package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


data class PageOthers(
    val watches : List<INotification>,
    val comments : List<INotification>,
    val shouts : List<INotification>,
    val favorites : List<INotification>,
    val journalIds : List<JournalStub>) {

    companion object : IParserHttp<PageOthers> {
        override fun parseFromHttp(body: String): PageOthers {
            val doc: Document = Jsoup.parse(body)

            val watches: List<INotification> = getWatches(doc.getElementById("messages-watches"))
            val comments: List<INotification> = getSubmissionComments(doc.getElementById("messages-comments-submission"))
            val shouts: List<INotification> = getShouts(doc.getElementById("messages-shouts"))
            val favorites: List<INotification> = getFavorites(doc.getElementById("messages-favorites"))
            val journalIds: List<JournalStub> = getJournalStubs(doc.getElementById("messages-journals"))

            return PageOthers(watches, comments, shouts, favorites, journalIds)
        }

        private fun getWatches(elementData: Element?): List<INotification> {
            if (elementData == null) {
                return listOf()
            }

            val notes: MutableList<NotificationWatch> = mutableListOf()
            val items = elementData.select("li")
            for (li in items) {
                notes.add(NotificationWatch.parseFromElement(li))
            }
            return notes.toList()
        }

        private fun getSubmissionComments(elementData: Element?): List<INotification> {
            if (elementData == null) {
                return listOf()
            }

            val notes: MutableList<NotificationComment> = mutableListOf()
            val items = elementData.select("li")
            for (li in items) {
                notes.add(NotificationComment.parseFromElement(li))
            }
            return notes.toList()
        }

        private fun getShouts(elementData: Element?): List<INotification> {
            if (elementData == null) {
                return listOf()
            }

            val notes: MutableList<NotificationShout> = mutableListOf()
            val items = elementData.select("li")
            for (li in items) {
                notes.add(NotificationShout.parseFromElement(li))
            }
            return notes.toList()
        }

        private fun getFavorites(elementData: Element?): List<INotification> {
            if (elementData == null) {
                return listOf()
            }

            val notes: MutableList<NotificationFavorite> = mutableListOf()
            val items = elementData.select("li")
            for (li in items) {
                notes.add(NotificationFavorite.parseFromElement(li))
            }
            return notes.toList()
        }

        private fun getJournalStubs(elementData: Element?): List<JournalStub> {
            if (elementData == null) {
                return listOf()
            }

            val cells = elementData.getElementsByTag("li")
            val divs: List<Element> = cells.map { it.child(0).child(1) }
            val stubs: List<JournalStub> = divs.map { JournalStub.parseFromElement(it) }

            return stubs
        }

    }
}
