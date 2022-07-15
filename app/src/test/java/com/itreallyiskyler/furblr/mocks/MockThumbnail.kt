package com.itreallyiskyler.furblr.mocks

import com.itreallyiskyler.furblr.enum.AgeRating
import com.itreallyiskyler.furblr.networking.models.IThumbnail

data class MockThumbnail(
    override var postId: Long,
    override var ageRating: AgeRating,
    override var imageSrc: String,
    override var imageWidth: Float,
    override var imageHeight: Float,
    override var title: String,
    override var creatorName: String
) : IThumbnail {}
