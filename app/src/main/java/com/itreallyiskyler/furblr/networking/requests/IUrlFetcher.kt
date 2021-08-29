package com.itreallyiskyler.furblr.networking.requests

import java.net.URL

interface IUrlFetcher {
    fun getUrl() : URL;
}