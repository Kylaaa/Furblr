package com.itreallyiskyler.furblr.models

interface BaseThumbnail {
    val postId: Long
    val creatorName: String
    val creatorId: Long
    val imageId: Long
    val imageHeight: Float
    val imageWidth: Float
    val title: String
}
