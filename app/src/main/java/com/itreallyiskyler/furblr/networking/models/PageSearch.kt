package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class PageSearch (
    val results : List<ThumbnailSubmission>
) {

    companion object : IParserHttp<PageSearch> {
        override fun parseFromHttp(body: String): PageSearch {
            val doc : Document = Jsoup.parse(body)
            val items : Elements = doc.select("figure")

            val results : List<ThumbnailSubmission> = items.map {
                    element -> ThumbnailSubmission.parseFromElement(element)
            }
            return PageSearch(results)
        }
    }
}
