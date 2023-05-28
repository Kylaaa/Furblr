package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.AgeRating
import org.jsoup.nodes.Element

/*
    Example Submission :

    <figure id="sid-43513245" class="r-general t-image">
        <b>
            <u>
                <a href="/view/43513245/">
                    <img alt="" src="//t.furaffinity.net/43513245@200-1630477795.jpg" data-width="160" data-height="200" style="width:160px; height:200px">
                    <i title="Click for description"></i>
                </a>
            </u>
        </b>
        <figcaption>
            <label for="input-43513245">
                <div>
                    <input id="input-43513245" type="checkbox" name="submissions[]" value="43513245">
                </div>
                <p>
                    <a href="/view/43513245/" title="Nyx the lombax">Nyx the lombax</a>
                </p>
                <p>
                    <i>by</i>
                    <a href="/user/kyander/" title="Kyander">Kyander</a>
                </p>
            </label>
        </figcaption>
    </figure>
 */

data class ThumbnailSubmission(
    override val postId: Long,
    override val creatorName: String,
    override val imageSrc: String,
    override val imageHeight: Float,
    override val imageWidth: Float,
    override val title: String,
    override val ageRating : AgeRating
) : IThumbnail {

    companion object : IParserElement<ThumbnailSubmission> {
        override fun parseFromElement(container: Element): ThumbnailSubmission {
            val imageData = container.select("img")[0]
            val captionData = container.select("p")
            val metaData = container.className().split(" ")

            val postId: Long = container.id().split("-")[1].toLong();
            val creatorName: String = captionData[1].child(1).text()
            val imageSrc: String = "https:" + imageData.attr("src")
            val imageHeight: Float = imageData.attr("data-height").toFloat()
            val imageWidth: Float = imageData.attr("data-width").toFloat()
            val title: String = captionData[0].child(0).text()
            val ageRating: AgeRating = AgeRating.fromString(metaData[0])

            return ThumbnailSubmission(postId, creatorName, imageSrc, imageHeight, imageWidth, title, ageRating)
        }
    }
}


