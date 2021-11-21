package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.home.HomePagePost
import com.itreallyiskyler.furblr.util.Promise


fun FetchHomePageContent(dbImpl : AppDatabase,
                         page : Int = 0,
                         pageSize : Int = 48,
                         forceRefresh : Boolean) : Promise {

    if (!forceRefresh) {
        // search for posts that we've already fetched
        var submissions: List<HomePagePost> = listOf()
        var currentPage = page
        while (submissions.size < pageSize) {
            val knownPosts: List<Long> =
                dbImpl.homePageDao().getHomePagePostIdsByPage(pageSize, currentPage)
            if (knownPosts.size == 0) {
                break;
            }
            submissions = submissions + ClobberHomePageContentById(dbImpl, knownPosts)
            currentPage += 1
        }

        val filteredSet = submissions.subList(0, pageSize).sortedByDescending { homePagePost -> homePagePost.postData.date }
        return Promise.resolve(filteredSet)
    }


    return Promise(fun(resolve, reject) {
        var submissions: List<HomePagePost> = listOf()

        lateinit var fetchNextPage : (Int)->Unit
        fetchNextPage = fun(pageOffset : Int) {
            FetchPageOfHome(dbImpl, page + pageOffset, pageSize, forceRefresh).then(
                fun(homePagePosts : Any?) {
                    submissions += (homePagePosts as List<HomePagePost>)
                    if ((homePagePosts as List<HomePagePost>).size <= 1) {
                        resolve(submissions)
                    }
                    else if (submissions.size < pageSize) {
                        fetchNextPage(pageOffset + 1)
                    }
                    else {
                        var filteredList = submissions.subList(0, pageSize)
                        resolve(filteredList)
                    }
                },
                fun(fetchErr) {
                    reject(fetchErr)
                })
        };

        fetchNextPage(0);
    });
}