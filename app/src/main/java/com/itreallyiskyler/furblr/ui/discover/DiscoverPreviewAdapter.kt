package com.itreallyiskyler.furblr.ui.discover

import android.view.View
import android.widget.ImageView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.ui.AdapterFactory
import com.squareup.picasso.Picasso

class DiscoverPreviewAdapter(
    initialDataSet : List<IHomePageContent> = listOf()
) : AdapterFactory<IHomePageContent>(
    initialDataSet,
    R.layout.listitem_discover_preview
) {
    private val imageLoader : Picasso = Picasso.get()

    override fun onRenderItem(view: View, content: IHomePageContent) {
        val imagePostDetails = content as HomePageImagePost

        // Define UI Element bindings here
        val postImageView : ImageView = view.findViewById(R.id.imgPreview)
        val postUrl = imagePostDetails.postData.submissionImgUrl
        imageLoader.load(postUrl).into(postImageView)
    }
}