package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.networking.requests.RequestView
import com.itreallyiskyler.furblr.util.Promise

fun FetchContentForPostIds(postIds : Collection<Long>, fetchReason : ContentFeedId) : Promise {
    val networkingManager = SingletonManager.get().NetworkingManager
    val fetchPromises: MutableList<Promise> = mutableListOf()
    postIds.forEach { postId: Long ->
        // pull down details for each of the missing posts
        val promise = networkingManager.requestView(postId).fetchContent()
            .then(fun(details: Any?) {
                // save the information we get into local storage
                PersistPagePostDetails(postId, details as PagePostDetails, fetchReason)
            }, fun(errorDetails: Any?) {
                // TODO : signal that a page failed to load somehow
                SingletonManager.get().LoggingManager.getChannel().logError(
                    "$postId failed to load : $errorDetails")
            })
        fetchPromises.add(promise)
    }

    return Promise.all(fetchPromises.toTypedArray())
}