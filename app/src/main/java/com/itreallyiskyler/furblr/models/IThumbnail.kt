package com.itreallyiskyler.furblr.models

interface IThumbnail {
    var postId: Long
    var creatorName: String
    var creatorId: Long
    var imageId: Long
    var imageHeight: Float
    var imageWidth: Float
    var title: String
}
