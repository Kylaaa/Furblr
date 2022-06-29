package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class PageSubmissions (httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody);
    private var AllSubmissionElements : Elements = doc.select("figure");

    val Submissions : Array<ThumbnailSubmission> = parseSubmissions(AllSubmissionElements);

    private fun parseSubmissions(elements: Elements) : Array<ThumbnailSubmission>
    {
        return elements.map { ThumbnailSubmission(it) }.toTypedArray()
    }
}
