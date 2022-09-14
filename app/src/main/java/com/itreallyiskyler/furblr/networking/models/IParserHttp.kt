package com.itreallyiskyler.furblr.networking.models

// Parses an object from an http response body
interface IParserHttp<T> {
    fun parseFromHttp(body : String) : T
}