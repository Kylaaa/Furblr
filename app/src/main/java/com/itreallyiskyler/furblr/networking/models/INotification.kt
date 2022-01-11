package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.NotificationId


interface INotification {
    var kind : NotificationId
    var id : Long
    var sourceName : String
    var sourcePost : Long?
    var date : String
}
