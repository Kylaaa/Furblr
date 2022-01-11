package com.itreallyiskyler.furblr.ui.notifications

import com.itreallyiskyler.furblr.persistence.entities.*

data class NotificationsPagePost(
    val notification : Notification,
    val creator : User?,
    val post : Post?)
