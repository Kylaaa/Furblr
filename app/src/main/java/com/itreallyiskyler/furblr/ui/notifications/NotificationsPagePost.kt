package com.itreallyiskyler.furblr.ui.notifications

import com.itreallyiskyler.furblr.persistence.entities.*
import java.util.*

data class NotificationsPagePost(
    val date : String,
    val notifications : List<Notification>,
    val post : Map<Notification, Post>,
    val users : Map<Notification, User>)
