package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import com.itreallyiskyler.testhelpers.managers.MockNetworkingManager
import com.itreallyiskyler.testhelpers.managers.SingletonInitializer
import com.itreallyiskyler.testhelpers.persistence.DBTestClass
import com.itreallyiskyler.testhelpers.persistence.DBTestUtils
import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import kotlin.system.measureTimeMillis
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchPageOfHomeInstrumentedTest : DBTestClass() {

    @Before
    fun initManagers() {
        val networkManager = object : MockNetworkingManager() {
            override fun requestSubmissions(
                scrollDirection: SubmissionScrollDirection,
                pageSize: Int,
                offsetId: Long?
            ): IRequestAction {
                return object : IRequestAction {
                    override fun fetchContent(): Promise {
                        val result = PageSubmissions.parseFromHttp("")
                        return Promise.resolve(result)
                    }
                }
            }
        }
        SingletonInitializer.init(networkingManager = networkManager)
        val db = getDB()
        DBTestUtils.populateDB(db)
    }

    @Test
    fun localFetchResolvesQuickly() {

        //val fileContents : String = ResourceFetcher.ReadTextFromResource("")


        /*val timeMS = measureTimeMillis {
            FetchPageOfHome(
                forceRefresh = false
            )
        }
        Assert.assertTrue(timeMS < 100)*/
    }

}