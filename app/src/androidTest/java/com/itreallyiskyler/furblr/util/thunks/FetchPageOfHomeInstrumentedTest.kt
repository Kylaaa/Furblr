package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.testhelpers.persistence.DBTestClass
import com.itreallyiskyler.testhelpers.persistence.DBTestUtils
import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import kotlin.system.measureTimeMillis
import org.junit.Assert
import org.junit.Test

class FetchPageOfHomeInstrumentedTest : DBTestClass() {

    @Test
    fun localFetchResolvesQuickly() {
        val db = getDB()
        DBTestUtils.populateDB(db)
        val fileContents : String = ResourceFetcher.ReadTextFromResource("")
        val requestHandler : RequestHandler = { url, request, resolve, reject ->
            resolve(fileContents)
        }
        val loggingChannel = LoggingChannel()

        val timeMS = measureTimeMillis {
            FetchPageOfHome(
                dbImpl = db,
                forceRefresh = false,
                requestHandler = requestHandler,
                loggingChannel = loggingChannel
            )
        }
        Assert.assertTrue(timeMS < 100)
    }

}