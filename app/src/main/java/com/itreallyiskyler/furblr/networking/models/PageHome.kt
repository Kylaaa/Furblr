package com.itreallyiskyler.furblr.networking.models

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

data class PageHome (
    val recentSubmissions : List<IThumbnail>,
    val recentWritings : List<IThumbnail>,
    val recentMusic : List<IThumbnail>,
    val recentCrafting : List<IThumbnail>
) {

    companion object : IParserHttp<PageHome> {
        override fun parseFromHttp(body: String): PageHome {
            val doc : Document = Jsoup.parse(body)
            val sections = doc.select("section")

            val recentSubmissions = parseSubmissions(sections[1])
            val recentWritings = parseSubmissions(sections[3])
            val recentMusic = parseSubmissions(sections[5])
            val recentCrafting = parseSubmissions(sections[7])
            return PageHome(recentSubmissions, recentWritings, recentMusic, recentCrafting)
        }
        private fun parseSubmissions(section: Element) : List<IThumbnail> {
            val elements = section.select("figure")
            return elements.map { ThumbnailSubmission.parseFromElement(it) }.toList()
        }
    }
}
