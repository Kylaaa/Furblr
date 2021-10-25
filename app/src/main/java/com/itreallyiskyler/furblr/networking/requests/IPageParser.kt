package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.util.Promise

interface IPageParser<T> {
    fun fetchContent() : Promise;
}