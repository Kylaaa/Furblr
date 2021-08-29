package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.networking.requests.RequestSubmissions

object ContentManager {

    fun fetchSubmissions() {
        RequestSubmissions().getContent { pageSubmissions ->
            for (submission in pageSubmissions.Submissions)
            {
                println(submission.toString());
            }
        };
    }
}