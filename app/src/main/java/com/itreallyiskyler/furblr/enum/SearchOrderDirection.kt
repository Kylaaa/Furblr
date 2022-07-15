package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class SearchOrderDirection(val id : String) {
    Descending("desc"),
    Ascending("asc");

    companion object {
        fun fromString(className: String): SearchOrderDirection {
            var mapping: Map<String, SearchOrderDirection> = mapOf(
                Pair("desc", SearchOrderDirection.Descending),
                Pair("asc", SearchOrderDirection.Ascending),
            );
            if (!mapping.containsKey(className)) {
                throw InvalidKeyException(className + " is not a valid type of SearchOrderDirection")
            }
            return mapping.getValue(className);
        }
    }
}