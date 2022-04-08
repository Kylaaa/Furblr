package com.itreallyiskyler.furblr.ui.notifications

import com.itreallyiskyler.furblr.persistence.entities.*

data class NotificationsPagePost(
    val date : String,
    val notifications : List<Notification>,
    val post : Map<Notification, View>,
    val users : Map<Notification, User>)
