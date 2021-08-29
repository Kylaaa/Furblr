package com.itreallyiskyler.furblr.models

abstract class ThumbnailAudio(httpBlob: String) : IThumbnail {
    init {
        postId = 0
        creatorName = "Test Creator Name"
        creatorId = 0
        imageId = 0
        imageHeight = 100.0f
        imageWidth = 100.0f
        title = "Test Title"
    }
}


