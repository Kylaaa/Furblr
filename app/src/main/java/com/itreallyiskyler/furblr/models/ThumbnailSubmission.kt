package com.itreallyiskyler.furblr.models

import org.jsoup.nodes.Element
import java.net.URI

class ThumbnailSubmission(elementData: Element) : IThumbnail {
    private var imageData = elementData.select("img")[0]
    private var captionData = elementData.select("p")

    override var postId: Long = elementData.id().split("-")[1].toLong();
    override var creatorName: String = captionData[1].child(1).text()
    override var imageSrc: String = "https:" + imageData.attr("src")
    override var imageHeight: Float = imageData.attr("data-height").toFloat()
    override var imageWidth: Float = imageData.attr("data-width").toFloat()
    override var title: String = captionData[0].child(0).text()
}


