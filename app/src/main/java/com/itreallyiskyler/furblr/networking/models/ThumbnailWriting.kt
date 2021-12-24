package com.itreallyiskyler.furblr.networking.models

abstract class ThumbnailWriting(_: String) : IThumbnail {
    // TODO : figure out if this class can be unified
    // TODO : parse httpBlob
    init {
        postId = 0
        creatorName = "Test Creator Name"
        imageSrc = "https://www.test.com/"
        imageHeight = 100.0f
        imageWidth = 100.0f
        title = "Test Title"
    }
}


