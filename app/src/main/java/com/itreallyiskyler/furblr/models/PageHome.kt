package com.itreallyiskyler.furblr.models

class PageHome (private val httpBody : String) {
    val RecentSubmissions : Array<ThumbnailSubmission> = parseSubmissions(httpBody);
    val RecentWritings : Array<ThumbnailWriting> = parseWriting(httpBody);
    val RecentMusic : Array<ThumbnailAudio> = parseAudio(httpBody);
    val RecentCrafting : Array<ThumbnailCrafting> = parseCrafting(httpBody);

    fun isLoginPage () : Boolean {
        return httpBody.contains("Please log in!")
    }

    private fun parseSubmissions(httpBody: String) : Array<ThumbnailSubmission>
    {
        return emptyArray<ThumbnailSubmission>();
    }

    private fun parseWriting(httpBody: String) : Array<ThumbnailWriting>
    {
        return emptyArray<ThumbnailWriting>();
    }

    private fun parseAudio(httpBody: String) : Array<ThumbnailAudio>
    {
        return emptyArray<ThumbnailAudio>();
    }

    private fun parseCrafting(httpBody: String) : Array<ThumbnailCrafting>
    {
        return emptyArray<ThumbnailCrafting>();
    }

}
