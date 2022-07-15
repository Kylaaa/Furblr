package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class SearchRange(val id : String) {
    Day("day"),
    ThreeDays("3days"),
    Week("week"),
    Month("month"),
    All("all");

    companion object {
        fun fromString(className: String): SearchRange {
            var mapping: Map<String, SearchRange> = mapOf(
                Pair("day", SearchRange.Day),
                Pair("3days", SearchRange.ThreeDays),
                Pair("week", SearchRange.Week),
                Pair("month", SearchRange.Month),
                Pair("all", SearchRange.All),
            );
            if (!mapping.containsKey(className)) {
                throw InvalidKeyException(className + " is not a valid type of SearchRange")
            }
            return mapping.getValue(className);
        }
    }
}