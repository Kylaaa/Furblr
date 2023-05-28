package com.itreallyiskyler.furblr.util

import android.content.Context
import java.lang.IndexOutOfBoundsException
import com.itreallyiskyler.furblr.R

class DateFormatter (longDate : String){
    companion object {
        private val monthValues : Map<String, Int> = mapOf(
            // English
            Pair("Jan",  1),
            Pair("Feb",  2),
            Pair("Mar",  3),
            Pair("Apr",  4),
            Pair("May",  5),
            Pair("Jun",  6),
            Pair("Jul",  7),
            Pair("Aug",  8),
            Pair("Sep",  9),
            Pair("Oct", 10),
            Pair("Nov", 11),
            Pair("Dec", 12)
        )

        fun getIndexFromName(monthName : String) : Int {
            if (!monthValues.containsKey(monthName)){
                throw IndexOutOfBoundsException("$monthName is not a valid month name!")
            }

            return monthValues.getValue(monthName)
        }

        fun createDate(year: Int, month : Int, day : Int, hour : Int, minute : Int) : String {
            val fmt = "%04d-%02d-%02dT%02d:%02d"
            return fmt.format(year, month, day, hour, minute)
        }

        fun convertToReadableDate(formattedDate : String, context : Context) : String {
            // convert a date like 2000-01-01T01:01 to Jan 1, 2000
            val year  : Int = formattedDate.substring(0, 4).toInt()
            val month : Int = formattedDate.substring(5, 7).toInt()
            val day   : Int = formattedDate.substring(8, 10).toInt()

            val keys = listOf<Int>(
                R.string.date_month_1,
                R.string.date_month_2,
                R.string.date_month_3,
                R.string.date_month_4,
                R.string.date_month_5,
                R.string.date_month_6,
                R.string.date_month_7,
                R.string.date_month_8,
                R.string.date_month_9,
                R.string.date_month_10,
                R.string.date_month_11,
                R.string.date_month_12,
            )
            val monthString : String = context.getString(keys[month - 1])

            val fmt = "%s %d, %d"
            return fmt.format(monthString, day, year)
        }
    }

    private val dateParts = getParts(longDate)
    val year : Int  = dateParts[0]
    val month : Int = dateParts[1]
    val day : Int   = dateParts[2]
    val hour : Int  = dateParts[3]
    val min : Int   = dateParts[4]

    fun toYYYYMMDDhhmm() : String {
        return createDate(year, month, day, hour, min)
    }

    private fun getParts(longDate: String) : List<Int> {
        // split up a date string like this : Jul 19, 2020 04:14 PM
        var groups = longDate.split(" ,", " ", ",", ":", ignoreCase = true).toMutableList()
        if (groups.size == 8) {
            groups.removeAt(0)
        }

        val month : Int = DateFormatter.getIndexFromName(groups[0])
        val day : Int = groups[1].filter { it.isDigit() }.toInt()
        val year : Int = groups[3].toInt()
        val hour : Int = groups[4].toInt() + (if (groups[6] == "PM") 12 else 0)
        val min : Int = groups[5].toInt()

        return listOf(year, month, day, hour, min)
    }
}