package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.networking.models.PageSearch
import com.itreallyiskyler.furblr.networking.requests.RequestSearch
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.util.Promise

fun FetchPageOfSearch(dbImpl : AppDatabase,
                    keyword : String) : Promise {

    return RequestSearch(keyword).fetchContent()

        .then(fun(pageSearch: Any?): Promise {
            val submissions = pageSearch as PageSearch
            // parse the results and persist them
            return Promise.resolve(submissions)
        }, fun(_: Any?) {
            println("Failed to fetch ")
        })
}