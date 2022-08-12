package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.managers.NetworkingManager
import com.itreallyiskyler.furblr.networking.models.PageUserDetails
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.networking.requests.RequestUser
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

fun FetchUsersByUsernames(
    dbImpl : AppDatabase,
    requestHandler: RequestHandler,
    loggingChannel: LoggingChannel = NetworkingManager.logChannel,
    usernames : Collection<String>
) : Promise {

    val fetchPromises: MutableList<Promise> = mutableListOf()

    usernames.forEach { username: String ->
        // pull down details for each of the missing posts
        fetchPromises.add(
            RequestUser(username, requestHandler, loggingChannel).fetchContent()
            .then(fun(details: Any?) {

                // save the information we get into local storage
                PersistUserDetails(
                    dbImpl,
                    details as PageUserDetails
                )
            }, fun(errorDetails: Any?) {
                // TODO : signal that a page failed to load somehow
                loggingChannel.logError("$usernames failed to load : $errorDetails")
            }))
    }

    return Promise.all(fetchPromises.toTypedArray())
}