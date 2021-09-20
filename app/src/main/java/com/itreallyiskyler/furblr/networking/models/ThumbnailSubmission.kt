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

class ThumbnailSubmission(elementData: Element) : IThumbnail {
    private var imageData = elementData.select("img")[0]
    private var captionData = elementData.select("p")
    private var metaData = elementData.className().split(" ");

    override var postId: Long = elementData.id().split("-")[1].toLong();
    override var creatorName: String = captionData[1].child(1).text()
    override var imageSrc: String = "https:" + imageData.attr("src")
    override var imageHeight: Float = imageData.attr("data-height").toFloat()
    override var imageWidth: Float = imageData.attr("data-width").toFloat()
    override var title: String = captionData[0].child(0).text()
    override var ageRating: AgeRating = AgeRating.fromString(metaData[0]);
}


