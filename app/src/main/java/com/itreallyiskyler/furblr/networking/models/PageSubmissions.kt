package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

data class PageSubmissions(
    val submissions : List<IThumbnail>
) {
    companion object : IParserHttp<PageSubmissions>{
        override fun parseFromHttp(httpBody : String): PageSubmissions {
            val doc : Document = Jsoup.parse(httpBody)
            val allSubmissionElements : Elements = doc.select("figure")
            val submissions = parseSubmissions(allSubmissionElements)
            return PageSubmissions(submissions)
        }

        private fun parseSubmissions(elements: Elements) : List<ThumbnailSubmission>
        {
            return elements.map {
                ThumbnailSubmission.parseFromElement(it)
            }.toList()
        }
    }
}
