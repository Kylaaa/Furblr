package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.enum.SearchMode
import com.itreallyiskyler.furblr.enum.SearchOrderBy
import com.itreallyiskyler.furblr.enum.SearchOrderDirection
import com.itreallyiskyler.furblr.enum.SearchRange
import com.itreallyiskyler.furblr.networking.models.PageSearch
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.util.Promise

class RequestSearch (
    private val keyword : String = "",
    private val searchOptions : SearchOptions = SearchOptions()
) : IPageParser<PageSearch>,
    BaseRequest(BuildConfig.BASE_URL, "search?q=$keyword") {

    private fun getFormValueForBool(boolValue : Boolean) : Any {
        return if (boolValue) "on" else 0
    }

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageSearch {
            return PageSearch(httpBody as String)
        }
        var failure = fun(message : Any?) {
            // TODO("Not yet implemented")
            println(message)
        }

        val data : HashMap<String, Any>? = hashMapOf(
            Pair("q", keyword),
            Pair("order-by", searchOptions.orderBy.id),
            Pair("order-direction", searchOptions.orderDirection.id),
            Pair("range", searchOptions.range.id),
            Pair("rating-general", getFormValueForBool(searchOptions.includeGeneralContent)),
            Pair("rating-mature", getFormValueForBool(searchOptions.includeMatureContent)),
            Pair("rating-adult", getFormValueForBool(searchOptions.includeAdultContent)),
            Pair("type-art", getFormValueForBool(searchOptions.includeArt)),
            Pair("type-music", getFormValueForBool(searchOptions.includeMusic)),
            Pair("type-flash", getFormValueForBool(searchOptions.includeFlash)),
            Pair("type-story", getFormValueForBool(searchOptions.includeStory)),
            Pair("type-photo", getFormValueForBool(searchOptions.includePhoto)),
            Pair("type-poetry", getFormValueForBool(searchOptions.includePoetry)),
            Pair("mode", searchOptions.mode.id)
        )

        return POST(data).then(success, failure)
    }
}