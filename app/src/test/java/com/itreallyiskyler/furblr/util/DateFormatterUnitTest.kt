package com.itreallyiskyler.furblr.util

import org.junit.Assert.*
import org.junit.Test

class DateFormatterUnitTest {
    @Test
    fun constructor_parsesLongFormDates() {
        val testDate = "Dec 5, 2021 11:45 AM"
        val df = DateFormatter(testDate)

        assertEquals(df.month, 12)
        assertEquals(df.day, 5)
        assertEquals(df.year, 2021)
        assertEquals(df.hour, 11)
        assertEquals(df.min, 45)
    }

    @Test
    fun constructor_parsesLongPMTimesProperly() {
        val testDate = "Dec 5, 2021 11:45 PM"
        val df = DateFormatter(testDate)

        assertEquals(df.month, 12)
        assertEquals(df.day, 5)
        assertEquals(df.year, 2021)
        assertEquals(df.hour, 23)
        assertEquals(df.min, 45)
    }

    @Test
    fun constructor_parsesLongEnglishTimesProperly() {
        val testDate = "Dec 5th, 2021 11:45 PM"
        val df = DateFormatter(testDate)

        assertEquals(df.month, 12)
        assertEquals(df.day, 5)
        assertEquals(df.year, 2021)
        assertEquals(df.hour, 23)
        assertEquals(df.min, 45)
    }

    @Test
    fun constructor_parsesLongTimesWithPrefixesProperly() {
        val testDate = "on Dec 5th, 2021 11:45 PM"
        val df = DateFormatter(testDate)

        assertEquals(df.month, 12)
        assertEquals(df.day, 5)
        assertEquals(df.year, 2021)
        assertEquals(df.hour, 23)
        assertEquals(df.min, 45)
    }

    @Test
    fun toYYYYMMDDhhmm_standardizesDates() {
        val testDate = "Dec 5, 2021 11:45 AM"
        val df = DateFormatter(testDate)
        val date = df.toYYYYMMDDhhmm()

        assertEquals(date, "2021-12-05T11:45")
    }

    @Test
    fun createDate_generatesAStringWithValues() {
        val date = DateFormatter.createDate(2000, 1, 1, 1, 1)

        assertEquals(date, "2000-01-01T01:01")
    }
}