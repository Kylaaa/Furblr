package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.SearchKeywordMatching
import com.itreallyiskyler.furblr.enum.SearchOrderBy
import com.itreallyiskyler.furblr.enum.SearchOrderDirection
import com.itreallyiskyler.furblr.enum.SearchRange

data class SearchOptions(
    var orderBy : SearchOrderBy = SearchOrderBy.Relevancy,
    var orderDirection : SearchOrderDirection = SearchOrderDirection.Descending,
    var range : SearchRange = SearchRange.All,
    var keywordMatching : SearchKeywordMatching = SearchKeywordMatching.All,
    var includeGeneralContent : Boolean = true,
    var includeMatureContent : Boolean = true,
    var includeAdultContent : Boolean = true,
    var includeArt : Boolean = true,
    var includeMusic : Boolean = false,
    var includeFlash : Boolean = false,
    var includeStory : Boolean = false,
    var includePhoto : Boolean = false,
    var includePoetry : Boolean = false
)
