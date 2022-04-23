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
import com.google.android.flexbox.FlexboxLayout
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.requests.RequestAvatarUrl
import com.itreallyiskyler.furblr.util.ContentManager
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.lang.IllegalArgumentException


class HomePageAdapter(
    initialDataSet : List<IHomePageContent> = listOf(),
    viewDisplayOptions: HomePageDisplayOptions = HomePageDisplayOptions()
) :
    RecyclerView.Adapter<HomePageAdapter.ViewHolder>()
{
    private var dataSet : List<IHomePageContent> = initialDataSet
    private val displayOptions : HomePageDisplayOptions = viewDisplayOptions

    class ViewHolder(
        val view: View,
        val viewContext : Context) : RecyclerView.ViewHolder(view)
    {
        private lateinit var viewOptions: HomePageDisplayOptions
        private var currentPost : IHomePageContent? = null
        private val loader = Picasso.get()

        private fun bindImagePost(view:View, content : IHomePageContent) {
            val imagePostDetails = content as HomePageImagePost

            // Define UI Element bindings here
            val layout : ConstraintLayout = view.findViewById(R.id.viewHomeSubmission)
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

            if (!viewOptions.fitHorizontal) {
                val imgLayoutParams = postImageView.layoutParams
                imgLayoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                imgLayoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                postImageView.layoutParams = imgLayoutParams

                val viewLayoutParams = layout.layoutParams
                viewLayoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                viewLayoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                layout.layoutParams = viewLayoutParams
            }

            val postUrl = imagePostDetails.postData.submissionImgUrl
            loader.load(postUrl).into(postImageView)

            if (viewOptions.showDetails) {
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

                val avatarUrl = RequestAvatarUrl(
                    imagePostDetails.postCreator.username,
                    imagePostDetails.postCreator.avatarId).getUrl().toString()
                loader.load(avatarUrl).into(avatarImageView)

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
                        println("Failed to bind tags to post. $ex")
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
            else {
                imgCommentsIcon.visibility = View.GONE
                imgFavesIcon.visibility = View.GONE
                imgViewsIcon.visibility = View.GONE
            }
        }

        private fun bindTextPost(view:View, content : IHomePageContent) {
            val textPostDetails = content as HomePageTextPost

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
            loader.load(avatarUrl).into(avatarImageView)
        }

        private fun bindUnknown(view:View, content : IHomePageContent) {
            val titleTextView : TextView = view.findViewById(R.id.txtTitle)
            titleTextView.text = "Unsupported : " + content.contentId.toString()
        }

        // bind data with the UI Elements
        fun bind(postDetails : IHomePageContent, viewDisplayOptions: HomePageDisplayOptions) {
            currentPost = postDetails
            viewOptions = viewDisplayOptions

            val kindMap : Map<PostKind, (View, IHomePageContent)->Unit> = mapOf(
                Pair(PostKind.Image, ::bindImagePost),
                Pair(PostKind.Journal, ::bindTextPost),
            )

            if (kindMap.containsKey(postDetails.postKind)) {
                kindMap.getValue(postDetails.postKind)(view, postDetails)
            }
            else {
                bindUnknown(view, postDetails)
                //throw Exception("Unsupported PostKind : ${postDetails.postKind}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutId : Int = -1
        when (viewType) {
            1 -> layoutId = R.layout.listitem_home_submission
            2 -> layoutId = R.layout.listitem_home_journal
            3 -> layoutId = R.layout.listitem_home_unknown
            else -> throw IllegalArgumentException("Cannot create ViewHolder of type : $viewType");
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postDetails = dataSet[position]
        holder.bind(postDetails, displayOptions)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    override fun getItemViewType(position: Int): Int {
        val itemAtIndex : IHomePageContent = dataSet[position]
        val kindMap : Map<PostKind, Int> = mapOf(
            Pair(PostKind.Image, 1),
            Pair(PostKind.Journal, 2),
        )

        if (kindMap.containsKey(itemAtIndex.postKind)) {
            return kindMap.getValue(itemAtIndex.postKind)
        }
        else {
            return 3
        }

        return super.getItemViewType(position)
    }

    fun updateData(newDataSet : List<IHomePageContent> = listOf()) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}