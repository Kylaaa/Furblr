package com.itreallyiskyler.furblr.ui.notifications

import androidx.lifecycle.ViewModel
import com.itreallyiskyler.furblr.util.Signal
import com.itreallyiskyler.furblr.util.Signal2
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList

class NotificationsViewModel : ViewModel() {

    // TODO : figure out how to page this nonsense
    val notes = SynchronizedLiveDataList<NotificationsPagePost>(listOf())
    val NotificationsReady = Signal<List<NotificationsPagePost>>()
    val NotificationsUpdated = Signal2<Int, NotificationsPagePost>()

    private var notificationsPagePosts : MutableList<NotificationsPagePost> = mutableListOf()

    fun setNotifications(content : List<NotificationsPagePost>)
    {
        notificationsPagePosts = content.toMutableList()
        notes.loadData(content)
        NotificationsReady.fire(notificationsPagePosts.toList())
    }

    fun updateNotifications(postId : Long, content : NotificationsPagePost) {
        /*if (!notificationsIndices.containsKey(postId)) {
            throw IndexOutOfBoundsException("Could not find $postId in notificationsIndices")
        }

        val index: Int = notificationsIndices[postId]!!
        notificationsPagePosts[index] = content

        notes.reloadDataEntry(index, content)
        NotificationsUpdated.fire(index, content)*/
    }


}