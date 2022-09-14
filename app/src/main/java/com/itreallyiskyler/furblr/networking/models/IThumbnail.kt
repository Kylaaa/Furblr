package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.AgeRating

interface IThumbnail {
    val postId: Long
    val creatorName: String
    val imageSrc: String
    val imageHeight: Float
    val imageWidth: Float
    val title: String
    val ageRating : AgeRating
}
