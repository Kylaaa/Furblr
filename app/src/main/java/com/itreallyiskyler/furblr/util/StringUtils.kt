package com.itreallyiskyler.furblr.util

object StringUtils {
    fun cleanUserId(userId: String) : String {
        return userId.replace("_", "", true).lowercase()
    }
}