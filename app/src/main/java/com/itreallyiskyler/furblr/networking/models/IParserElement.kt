package com.itreallyiskyler.furblr.networking.models

// Parses an object from an HTTP element
interface IParserElement<T> {
    fun parseFromElement(container : org.jsoup.nodes.Element) : T
}