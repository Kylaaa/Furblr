package com.itreallyiskyler.furblr.models

class PageSubmissions (httpBody : String) {
    val Submissions : Array<ThumbnailSubmission> = parseSubmissions(httpBody);

    private fun parseSubmissions(httpBody: String) : Array<ThumbnailSubmission>
    {
        return emptyArray<ThumbnailSubmission>();
    }
}
