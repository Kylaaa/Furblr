package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.managers.NetworkingManager
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import kotlin.concurrent.thread


fun FetchHomePageContent(
    dbImpl : AppDatabase,
    requestHandler: RequestHandler,
    loggingChannel: LoggingChannel = NetworkingManager.logChannel,
    page : Int = 0,
    pageSize : Int = 48,
    forceRefresh : Boolean) : Promise {

    if (!forceRefresh) {
        return Promise(fun(resolve : GenericCallback, reject : GenericCallback){
            thread(start = true, name = "FetchHomePageContentWithoutForceRefreshThread") {
                // search for posts that we've already fetched
                val ids: List<Long> = dbImpl.homePageDao().getHomePagePostIdsByPage(pageSize, page)
                resolve(ClobberHomePageImagesById(dbImpl, ids))
            }
        })
    }

    return Promise(fun(resolve, reject) {
        var submissions: List<HomePageImagePost> = listOf()

        lateinit var fetchNextPage : (Int)->Unit
        fetchNextPage = fun(pageOffset : Int) {
            FetchPageOfHome(dbImpl, requestHandler, loggingChannel, page + pageOffset, pageSize, forceRefresh).then(
                fun(homePagePosts : Any?) {
                    submissions += (homePagePosts as List<HomePageImagePost>)
                    if ((homePagePosts as List<HomePageImagePost>).size <= 1) {
                        resolve(submissions)
                    }
                    else if (submissions.size < pageSize) {
                        fetchNextPage(pageOffset + 1)
                    }
                    else {
                        val filteredList = submissions.subList(0, pageSize)
                        resolve(filteredList)
                    }
                },
                fun(fetchErr) {
                    reject(fetchErr)
                }
            )
        };

        fetchNextPage(0);
    });
}