package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId


interface INotification {
    val kind : NotificationId
    val id : Long
    val sourceName : String
    val sourcePost : Long?
    val date : String
}
