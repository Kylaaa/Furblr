package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig

class RequestAvatarUrl(
    username : String,
    avatarId : Long
) : BaseRequest(BuildConfig.ASSET_URL, "$avatarId/$username") {}