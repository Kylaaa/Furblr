package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.util.Promise

interface IRequestAction {
    fun performAction() : Promise;
}