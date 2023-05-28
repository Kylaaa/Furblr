package com.itreallyiskyler.furblr.ui.notifications

import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.util.Signal
import com.itreallyiskyler.furblr.util.Signal2
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList

class NotificationsViewModel : ViewModel() {

    // TODO : figure out how to page this nonsense
    private var unreadCount : Int = 0
    val notes = SynchronizedLiveDataList<NotificationsPagePost>(listOf())
    val NotificationsReady = Signal<List<NotificationsPagePost>>()
    val NotificationsUpdated = Signal2<Int, NotificationsPagePost>()
    val UnreadCountChanged = Signal<Int>()

    private var notificationsPagePosts : MutableList<NotificationsPagePost> = mutableListOf()

    fun setNotifications(content : List<NotificationsPagePost>)
    {
        notificationsPagePosts = content.toMutableList()
        notes.loadData(content)
        NotificationsReady.fire(notificationsPagePosts.toList())
    }

    fun updateUnreadNotifications(newCount : Int) {
        if (newCount != unreadCount) {
            unreadCount = newCount
            UnreadCountChanged.fire(unreadCount)
        }
    }

    fun updateNotifications(updateList : List<NotificationsPagePost>) {
        updateList.forEach {
            val index : Int? = notificationsPagePosts.indexOf(it)
            if (index != null) {
                notes.reloadDataEntry(index, it)
                NotificationsUpdated.fire(index, it)
            }
        }
    }


}