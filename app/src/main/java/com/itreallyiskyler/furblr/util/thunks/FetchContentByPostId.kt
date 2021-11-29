package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.networking.requests.RequestView
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.util.Promise

fun FetchContentForPostIds(dbImpl : AppDatabase,
                           postIds : Collection<Long>,
                           fetchReason : ContentFeedId) : Promise {

    val fetchPromises: MutableList<Promise> = mutableListOf()

    postIds.forEach { postId: Long ->
        // pull down details for each of the missing posts
        fetchPromises.add(
            RequestView(postId).fetchContent()
            .then(fun(details: Any?) {

                // save the information we get into local storage
                PersistPagePostDetails(
                    dbImpl,
                    postId,
                    details as PagePostDetails,
                    fetchReason
                )
            }, fun(errorDetails: Any?) {
                // TODO : signal that a page failed to load somehow
                println("$postId failed to load : $errorDetails")
            }))
    }

    return Promise.all(fetchPromises.toTypedArray())
}