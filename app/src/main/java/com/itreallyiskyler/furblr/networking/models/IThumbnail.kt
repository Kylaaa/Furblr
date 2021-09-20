package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.AgeRating

interface IThumbnail {
    var postId: Long
    var creatorName: String
    var imageSrc: String
    var imageHeight: Float
    var imageWidth: Float
    var title: String
    var ageRating : AgeRating
}
