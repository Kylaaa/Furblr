package com.itreallyiskyler.furblr.models

import java.net.URI

abstract class ThumbnailCrafting(httpBlob: String) : IThumbnail {
    init {
        postId = 0
        creatorName = "Test Creator Name"
        imageSrc = "https://www.test.com/"
        imageHeight = 100.0f
        imageWidth = 100.0f
        title = "Test Title"
    }
}


