package com.itreallyiskyler.furblr

import com.itreallyiskyler.testhelpers.persistence.DBTestClass
import com.itreallyiskyler.testhelpers.persistence.DBTestUtils
import org.junit.Assert
import org.junit.Test
import kotlin.system.measureTimeMillis

class ViewsDaoUnitTest : DBTestClass() {
    @Test
    fun insertOrUpdate_returnsExpectedResults() {
    }

    @Test
    fun getExistingViewsWithIds_returnsExpectedResults() {
    }

    @Test
    fun getExistingViewsWithIds_resolvesQuickly() {
        val ACCEPTABLE_TIME_FRAME_MS = 10
        val db = getDB()
        DBTestUtils.populateDB(db)
        val viewsDao = db.viewsDao()
        val viewsToFetch : List<Long> = DBTestUtils.getViewIdRange().toList()

        val time = measureTimeMillis {
            viewsDao.getExistingViewsWithIds(viewsToFetch)
        }
        Assert.assertTrue(time < ACCEPTABLE_TIME_FRAME_MS)

        val assortedViewsToFetch = viewsToFetch.shuffled()
        val time2 = measureTimeMillis {
            viewsDao.getExistingViewsWithIds(assortedViewsToFetch)
        }
        Assert.assertTrue(time2 < ACCEPTABLE_TIME_FRAME_MS)

        val lastIndex = DBTestUtils.getViewIdRange().last
        val missingViewsToFetch = (lastIndex .. (lastIndex + 48)).toList()
        val time3 = measureTimeMillis {
            viewsDao.getExistingViewsWithIds(missingViewsToFetch)
        }
        Assert.assertTrue(time3 < ACCEPTABLE_TIME_FRAME_MS)
    }
}