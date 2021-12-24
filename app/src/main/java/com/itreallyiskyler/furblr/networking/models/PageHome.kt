package com.itreallyiskyler.furblr.networking.models

class PageHome (private val httpBody : String) {
    val RecentSubmissions : Array<ThumbnailSubmission> = parseSubmissions(httpBody);
    val RecentWritings : Array<ThumbnailWriting> = parseWriting(httpBody);
    val RecentMusic : Array<ThumbnailAudio> = parseAudio(httpBody);
    val RecentCrafting : Array<ThumbnailCrafting> = parseCrafting(httpBody);

    fun isLoginPage () : Boolean {
        return httpBody.contains("Please log in!")
    }

    private fun parseSubmissions(_: String) : Array<ThumbnailSubmission>
    {
        // TODO : parse httpBody
        return emptyArray<ThumbnailSubmission>();
    }

    private fun parseWriting(_: String) : Array<ThumbnailWriting>
    {
        // TODO : parse httpBody
        return emptyArray<ThumbnailWriting>();
    }

    private fun parseAudio(_: String) : Array<ThumbnailAudio>
    {
        // TODO : parse httpBody
        return emptyArray<ThumbnailAudio>();
    }

    private fun parseCrafting(_: String) : Array<ThumbnailCrafting>
    {
        // TODO : parse httpBody
        return emptyArray<ThumbnailCrafting>();
    }
}
