package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageSearch
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

/*
// Basic search parameters
text box "q" = search query string
select box "order-by" = values {"relevancy", "date", "popularity"}
select box "order-direction" = values {"desc", "asc"}
radio buttons "range" = values {"day", "3days", "week", "month", "all"}
checkbox "rating-general" = "on" if checked (ignored otherwise)
checkbox "rating-mature"
checkbox "rating-adult"
checkbox "type-art"
checkbox "type-music"
checkbox "type-flash"
checkbox "type-story"
checkbox "type-photo"
checkbox "type-poetry"
select box "mode" = values {"all", "any", "extended"}

// Used only when inputting a new search query string
hidden "reset_page" = "1"
button "do_search" = "Search" confirms the search button was pressed

// Used only when navigating multiple pages of results
hidden "page" - page number of results that were shown
button "next_page" = "Next" if fetching (page+1) of the results
button "prev_page" = "Back" if fetching (page-1) of the results
(These last two groups of parameters are separated from each other and only one group is actually sent.
This is to fix a prior bug where the page number did not reset when performing a new search from the same page.)

Checkboxes send their value only if checked; if unchecked, they are omitted from the request.
The actual value can be defined by the checkbox itself, or the browser can send a default value ("1", "on", etc.) instead.
 */
class RequestSearch (
    private val keyword : String = "",
    private val searchOptions : SearchOptions = SearchOptions()
) : BaseRequest(BuildConfig.BASE_URL, "search?q=$keyword") {

    constructor(
        keyword: String,
        searchOptions: SearchOptions,
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel
    ) : this(keyword, searchOptions) {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    private val DO_NOT_INCLUDE : String = ""
    private fun getFormValueForBool(boolValue : Boolean) : String {
        return if (boolValue) "on" else DO_NOT_INCLUDE
    }

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageSearch {
            return PageSearch.parseFromHttp(httpBody as String)
        }
        var failure = fun(message : Any?) {
            getLogChannel().logError("Failed to fetch search results with error : $message")
        }

        val optionalParams = mapOf(
            Pair("rating-general", getFormValueForBool(searchOptions.includeGeneralContent)),
            Pair("rating-mature", getFormValueForBool(searchOptions.includeMatureContent)),
            Pair("rating-adult", getFormValueForBool(searchOptions.includeAdultContent)),
            Pair("type-art", getFormValueForBool(searchOptions.includeArt)),
            Pair("type-music", getFormValueForBool(searchOptions.includeMusic)),
            Pair("type-flash", getFormValueForBool(searchOptions.includeFlash)),
            Pair("type-story", getFormValueForBool(searchOptions.includeStory)),
            Pair("type-photo", getFormValueForBool(searchOptions.includePhoto)),
            Pair("type-poetry", getFormValueForBool(searchOptions.includePoetry))
        )

        val data : HashMap<String, Any>? = hashMapOf(
            Pair("q", keyword),
            Pair("order-by", searchOptions.orderBy.id),
            Pair("order-direction", searchOptions.orderDirection.id),
            Pair("range", searchOptions.range.id),
            Pair("mode", searchOptions.keywordMatching.id)
        )
        optionalParams.forEach { key, value ->
            if (value != DO_NOT_INCLUDE) {
                data?.set(key, value)
            }
        }

        return POST(data).then(success, failure)
    }
}