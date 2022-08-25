package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.managers.NetworkingManager
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageUserDetails
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.networking.requests.RequestUser
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

fun FetchUsersByUsernames(usernames : Collection<String>) : Promise {

    val fetchPromises: MutableList<Promise> = mutableListOf()

    usernames.forEach { username: String ->
        // pull down details for each of the missing posts
        fetchPromises.add(
            SingletonManager.get().NetworkingManager.requestUser(username).fetchContent()
            .then(fun(details: Any?) {

                // save the information we get into local storage
                PersistUserDetails(details as PageUserDetails)
            }, fun(errorDetails: Any?) {
                // TODO : signal that a page failed to load somehow
                SingletonManager.get().LoggingManager.getChannel().logError("$usernames failed to load : $errorDetails")
            }))
    }

    return Promise.all(fetchPromises.toTypedArray())
}