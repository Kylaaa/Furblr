package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.*
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistPageOthers(pagePostOthers: PageOthers) {
    val notes : MutableList<Notification> = mutableListOf()
    listOf(
        pagePostOthers.comments,
        pagePostOthers.favorites,
        pagePostOthers.watches,
        pagePostOthers.shouts).forEach {

        val newNotesList = it.map {
            Notification(
                it.id,
                it.kind.id,
                it.sourcePost,
                it.sourceName,
                it.date
            )
        }
        notes += newNotesList
    }

    val notificationsDao = SingletonManager.get().DBManager.getDB().notificationsDao()
    notes.forEach {
        notificationsDao.insertOrUpdateNotification(it)
    }
}