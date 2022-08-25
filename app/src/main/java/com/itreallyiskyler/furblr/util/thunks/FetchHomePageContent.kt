package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.Promise
import kotlin.concurrent.thread


fun FetchHomePageContent(page : Int = 0, pageSize : Int = 48, forceRefresh : Boolean) : Promise {

    if (!forceRefresh) {
        return Promise(fun(resolve : GenericCallback, _ : GenericCallback){
            thread(start = true, name = "FetchHomePageContentWithoutForceRefreshThread") {
                // search for posts that we've already fetched
                val homePageDao = SingletonManager.get().DBManager.getDB().homePageDao()
                val ids: List<Long> = homePageDao.getHomePagePostIdsByPage(pageSize, page)
                resolve(ClobberHomePageImagesById(ids))
            }
        })
    }

    return Promise(fun(resolve, reject) {
        val submissions: MutableList<HomePageImagePost> = mutableListOf()

        lateinit var fetchNextPage : (Int)->Unit
        fetchNextPage = fun(pageOffset : Int) {
            FetchPageOfHome(page + pageOffset, pageSize, forceRefresh).then(
                fun(homePagePosts : Any?) {
                    submissions += (homePagePosts as List<HomePageImagePost>)
                    if ((homePagePosts as List<HomePageImagePost>).size <= 1) {
                        resolve(submissions.toList())
                    }
                    else if (submissions.size < pageSize) {
                        fetchNextPage(pageOffset + 1)
                    }
                    else {
                        val filteredList = submissions.subList(0, pageSize).toList()
                        resolve(filteredList)
                    }
                },
                fun(fetchErr) {
                    reject(fetchErr)
                }
            )
        }

        fetchNextPage(0)
    })
}