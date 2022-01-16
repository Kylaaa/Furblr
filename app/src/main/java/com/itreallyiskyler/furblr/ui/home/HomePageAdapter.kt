package com.itreallyiskyler.furblr.ui.home

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.requests.RequestAvatarUrl
import com.itreallyiskyler.furblr.util.ContentManager
import java.lang.Exception
import java.lang.IllegalArgumentException


class HomePageAdapter(initialDataSet : List<IHomePageContent> = listOf()) :
    RecyclerView.Adapter<HomePageAdapter.ViewHolder>()
{
    private var dataSet : List<IHomePageContent> = initialDataSet

    class ViewHolder(val view: View, val viewContext : Context) : RecyclerView.ViewHolder(view)
    {
        private var currentPost : IHomePageContent? = null

        private fun bindImagePost(view:View, imagePostDetails : HomePageImagePost) {
            // Define UI Element bindings here
            val creatorTextView : TextView = view.findViewById(R.id.txtCreator)
            val titleTextView : TextView = view.findViewById(R.id.txtTitle)
            val viewsTextView : TextView = view.findViewById(R.id.txtViews)
            val favesTextView : TextView = view.findViewById(R.id.txtFaves)
            val commentsTextView : TextView = view.findViewById(R.id.txtComments)
            val postImageView : ImageView = view.findViewById(R.id.imgPost)
            val avatarImageView : ImageView = view.findViewById(R.id.imgAvatar)
            val imgFavesIcon : ImageView = view.findViewById(R.id.imgFavesIcon)
            val imgCommentsIcon : ImageView = view.findViewById(R.id.imgCommentsIcon)
            val imgViewsIcon : ImageView = view.findViewById(R.id.imgViewsIcon)
            val layoutTags : FlexboxLayout = view.findViewById(R.id.layoutTags)

            creatorTextView.text = imagePostDetails.postCreator.username
            titleTextView.text = imagePostDetails.postData.title
            viewsTextView.text = imagePostDetails.postData.viewCount.toString()
            favesTextView.text = imagePostDetails.postData.favoriteCount.toString()
            commentsTextView.text = imagePostDetails.postComments.count().toString()

            if (imagePostDetails.postData.hasFavorited) {
                imgFavesIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                imgFavesIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            val postUrl = imagePostDetails.postData.contentsId
            Glide.with(viewContext).load(postUrl).into(postImageView)

            val avatarUrl = RequestAvatarUrl(
                imagePostDetails.postCreator.username,
                imagePostDetails.postCreator.avatarId).getUrl().toString()
            Glide.with(viewContext).load(avatarUrl).into(avatarImageView)

            layoutTags.removeAllViews()
            val viewInflater = LayoutInflater.from(viewContext)
            imagePostDetails.postTags.forEach {
                try {
                    val layout =
                        viewInflater.inflate(R.layout.listitem_content_tag, null) as ConstraintLayout
                    layout.id = View.generateViewId()
                    val txtTag = layout.getChildAt(0) as TextView
                    txtTag.text = it.tagContents

                    layoutTags.addView(layout)
                }
                catch(ex : Exception)
                {
                    println(ex)
                }
            }

            imgFavesIcon.setOnClickListener {
                val postData = imagePostDetails.postData
                println("Favoriting ${postData.title}")
                //currentPost!!.postData.hasFavorited = !postData.hasFavorited

                ContentManager.favoritePost(imagePostDetails)

                // TODO : Figure out how to mutate this data, and have it be updated
            }

            imgCommentsIcon.setOnClickListener {
                println("Checking comments of ${imagePostDetails.postData.title}")
            }

            imgViewsIcon.setOnClickListener {
                println("Checking details of ${imagePostDetails.postData.title}")
            }
        }

        private fun bindTextPost(view:View, textPostDetails : HomePageTextPost) {
            // Define UI Element bindings here
            val creatorTextView : TextView = view.findViewById(R.id.txtCreator)
            val titleTextView : TextView = view.findViewById(R.id.txtTitle)
            val commentsTextView : TextView = view.findViewById(R.id.txtComments)
            val contentView : TextView = view.findViewById(R.id.txtContent)
            val avatarImageView : ImageView = view.findViewById(R.id.imgAvatar)

            creatorTextView.text = textPostDetails.postCreator.username
            titleTextView.text = textPostDetails.postData.title
            commentsTextView.text = textPostDetails.postComments.count().toString()
            contentView.text = Html.fromHtml(textPostDetails.postData.message, Html.FROM_HTML_MODE_LEGACY)

            val avatarUrl = RequestAvatarUrl(
                textPostDetails.postCreator.username,
                textPostDetails.postCreator.avatarId).getUrl().toString()
            Glide.with(viewContext).load(avatarUrl).into(avatarImageView)
        }

        // bind data with the UI Elements
        fun bind(postDetails : IHomePageContent) {
            currentPost = postDetails

            if (postDetails.postKind == PostKind.Image) { bindImagePost(view, postDetails as HomePageImagePost) }
            else if (postDetails.postKind == PostKind.Text) { bindTextPost(view, postDetails as HomePageTextPost) }
            else {
                throw Exception("Unsupported PostKind : ${postDetails.postKind}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutId : Int = -1
        when (viewType) {
            1 -> layoutId = R.layout.listitem_home_submission
            2 -> layoutId = R.layout.listitem_home_journal
            else -> throw IllegalArgumentException("Cannot create ViewHolder of type : $viewType");
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postDetails = dataSet[position]
        holder.bind(postDetails)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    override fun getItemViewType(position: Int): Int {
        val itemAtIndex : IHomePageContent = dataSet[position]
        if (itemAtIndex.postKind == PostKind.Image) { return 1 }
        else if (itemAtIndex.postKind == PostKind.Text) { return 2 }

        return super.getItemViewType(position)
    }

    fun updateData(newDataSet : List<IHomePageContent> = listOf())
    {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}