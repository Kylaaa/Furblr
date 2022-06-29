package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class PageSearch (private val httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody)
    private val items : Elements = doc.select("figure")

    val results : List<ThumbnailSubmission> = items.map { element -> ThumbnailSubmission(element) }
}
