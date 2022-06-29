package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class PageHome (httpBody : String) {
    private var doc : Document = Jsoup.parse(httpBody)
    private var sections = doc.select("section")

    val RecentSubmissions : Array<ThumbnailSubmission> = parseSubmissions(sections[1]);
    val RecentWritings : Array<ThumbnailSubmission> = parseSubmissions(sections[3]);
    val RecentMusic : Array<ThumbnailSubmission> = parseSubmissions(sections[5]);
    val RecentCrafting : Array<ThumbnailSubmission> = parseSubmissions(sections[7]);

    private fun parseSubmissions(section: Element) : Array<ThumbnailSubmission>
    {
        var elements = section.select("figure")
        return elements.map { ThumbnailSubmission(it) }.toTypedArray()
    }
}
