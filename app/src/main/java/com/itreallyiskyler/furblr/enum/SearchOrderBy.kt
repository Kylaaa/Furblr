package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class SearchOrderBy(val id : String) {
    Relevancy("relevancy"),
    Date("date"),
    Popularity("popularity");

    companion object {
        fun fromString(className: String): SearchOrderBy {
            var mapping: Map<String, SearchOrderBy> = mapOf(
                Pair("relevancy", SearchOrderBy.Relevancy),
                Pair("date", SearchOrderBy.Date),
                Pair("popularity", SearchOrderBy.Popularity),
            );
            if (!mapping.containsKey(className)) {
                throw InvalidKeyException(className + " is not a valid type of SearchOrderBy")
            }
            return mapping.getValue(className);
        }
    }
}