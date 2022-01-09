package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.networking.models.*
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*

private fun mapToNotificationList(notesList : List<INotification>) : List<Notification> {
    return notesList.map {
        Notification(
            it.id,
            it.kind.id,
            it.sourcePost,
            it.sourceName,
            it.date
        )
    }
}

fun PersistPageOthers (dbImpl: AppDatabase,
                        pagePostOthers: PageOthers) {
    var notes : List<Notification> = listOf()

    notes += mapToNotificationList(pagePostOthers.Comments)
    notes += mapToNotificationList(pagePostOthers.Favorites)
    notes += mapToNotificationList(pagePostOthers.Watches)
    notes += mapToNotificationList(pagePostOthers.Shouts)

    notes.forEach {
        dbImpl.notificationsDao().insertOrUpdateNotification(it)
    }
}