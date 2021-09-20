package com.itreallyiskyler.furblr.networking.models

import okhttp3.internal.toImmutableList
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class PageSubmissions (httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody);
    private var AllSubmissionElements : Elements = doc.select("figure");

    val Submissions : Array<ThumbnailSubmission> = parseSubmissions(AllSubmissionElements);

    private fun parseSubmissions(submissionElements: Elements) : Array<ThumbnailSubmission>
    {
        var submissions : MutableList<ThumbnailSubmission> = mutableListOf()
        submissionElements.forEach { element -> submissions.add(ThumbnailSubmission(element))}
        return submissions.toImmutableList().toTypedArray();
    }
}
