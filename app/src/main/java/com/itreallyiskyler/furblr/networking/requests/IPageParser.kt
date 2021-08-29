package com.itreallyiskyler.furblr.networking.requests

import javax.security.auth.callback.Callback

interface IPageParser<T> {
    fun getContent(callback: (T)-> Unit);
}