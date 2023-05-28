package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class SearchKeywordMatching(override val id : String) : IValueAccessor<String> {
    All("all"),
    Anything("any"),
    Extended("extended");

    companion object {
        fun fromString(className: String): SearchKeywordMatching {
            var mapping: Map<String, SearchKeywordMatching> = mapOf(
                Pair("all", SearchKeywordMatching.All),
                Pair("any", SearchKeywordMatching.Anything),
                Pair("extended", SearchKeywordMatching.Extended),
            );
            if (!mapping.containsKey(className)) {
                throw InvalidKeyException(className + " is not a valid type of SearchOrderBy")
            }
            return mapping.getValue(className);
        }
    }
}