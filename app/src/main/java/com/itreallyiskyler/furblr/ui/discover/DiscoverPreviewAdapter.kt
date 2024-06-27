package com.itreallyiskyler.furblr.ui.discover

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.ui.AdapterFactory

// Horizontal scrolling list showing previews of submissions in each section
class DiscoverPreviewAdapter(
    initialDataSet : List<IHomePageContent> = listOf()
) : AdapterFactory<IHomePageContent>(
    initialDataSet
) {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.listitem_discover_preview
    }

    override fun onRenderItem(view: View, content: IHomePageContent, viewContext: Context) {
        val imagePostDetails = content as HomePageImagePost

        // Define UI Element bindings here
        val postImageView : ImageView = view.findViewById(R.id.imgPreview)

        // load the image
        val postUrl = imagePostDetails.postData.submissionImgUrl
        val postHeight = imagePostDetails.postData.submissionImgSizeHeight
        val postWidth = imagePostDetails.postData.submissionImgSizeWidth
        val requestOptions = RequestOptions().fitCenter().override(postWidth, postHeight)
        Glide.with(view).load(postUrl).apply(requestOptions).into(postImageView)
    }
}