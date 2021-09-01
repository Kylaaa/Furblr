package com.itreallyiskyler.furblr.models

import java.net.URI

interface IThumbnail {
    var postId: Long
    var creatorName: String
    var imageSrc: String
    var imageHeight: Float
    var imageWidth: Float
    var title: String
}
