package com.itreallyiskyler.furblr.util

import java.lang.IndexOutOfBoundsException

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
            var fmt = "%04d-%02d-%02dT%02d:%02d"
            return fmt.format(year, month, day, hour, minute)
        }
    }

    // split up a date string like this : Jul 19, 2020 04:14 PM
    //private var rgx : Regex = Regex("([A-Za-z]+) ([0-9]+), ([0-9]{4}) ([0-9]{2}):([0-9]{2}) ([A-Z]{2})")
    //private var groups = longDate.split(rgx)
    private var groups = longDate.split(" ,", " ", ",", ":", ignoreCase = true)

    val month : Int = DateFormatter.getIndexFromName(groups[0])
    val day : Int = groups[1].toInt()
    val year : Int = groups[3].toInt()
    val hour : Int = groups[4].toInt() + (if (groups[6] == "PM") 12 else 0)
    val min : Int = groups[5].toInt()

    fun toYYYYMMDDhhmm() : String {
        return createDate(year, month, day, hour, min)
    }
}