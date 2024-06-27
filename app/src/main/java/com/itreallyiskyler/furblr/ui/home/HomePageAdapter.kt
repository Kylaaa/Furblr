package com.itreallyiskyler.furblr.ui.home

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayout
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.requests.RequestAvatarUrl
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.util.ui.AdapterFactory
import java.lang.IllegalArgumentException
import kotlin.Exception

class HomePageAdapter(
    initialDataSet : List<IHomePageContent> = listOf()
) : AdapterFactory<IHomePageContent>(initialDataSet)
{
    private val LAYOUT_UNKNOWN : Int = 0
    private val LAYOUT_IMAGE : Int = 1
    private val LAYOUT_JOURNAL : Int = 2
    private val LAYOUT_MUSIC : Int = 3
    private val LAYOUT_WRITING : Int = 4

    override fun getItemViewType(position: Int): Int {
        val itemAtIndex : IHomePageContent = dataSet[position]
        val kindMap : Map<PostKind, Int> = mapOf(
            Pair(PostKind.Image, LAYOUT_IMAGE),
            Pair(PostKind.Journal, LAYOUT_JOURNAL),
            Pair(PostKind.Writing, LAYOUT_WRITING),
            Pair(PostKind.Music, LAYOUT_MUSIC),
        )

        if (kindMap.containsKey(itemAtIndex.postKind)) {
            return kindMap.getValue(itemAtIndex.postKind)
        }
        else {
            return LAYOUT_UNKNOWN
        }

        return super.getItemViewType(position)
    }

    override fun getLayoutId(viewType: Int): Int {
        val layoutId : Int = when (viewType) {
            LAYOUT_IMAGE -> R.layout.listitem_home_submission
            LAYOUT_JOURNAL -> R.layout.listitem_home_journal
            LAYOUT_MUSIC -> R.layout.listitem_home_music
            LAYOUT_WRITING -> R.layout.listitem_home_submission
            LAYOUT_UNKNOWN -> R.layout.listitem_home_submission
            else -> throw IllegalArgumentException("Cannot create ViewHolder of type : $viewType");
        }
        return layoutId
    }

    override fun onRenderItem(view: View, content: IHomePageContent, viewContext: Context) {
        val kindMap : Map<PostKind, (View, IHomePageContent, Context)->Unit> = mapOf(
            Pair(PostKind.Image, ::bindImagePost),
            Pair(PostKind.Journal, ::bindTextPost),
            Pair(PostKind.Music, ::bindMusicPost),
            Pair(PostKind.Writing, ::bindWritingPost),
            Pair(PostKind.Unknown, ::bindImagePost),
        )

        if (kindMap.containsKey(content.postKind)) {
            kindMap.getValue(content.postKind)(view, content, viewContext)
        }
        else {
            bindUnknown(view, content, viewContext)
            //throw Exception("Unsupported PostKind : ${postDetails.postKind}")
        }
    }

    private fun bindImagePost(view : View, content : IHomePageContent, viewContext: Context) {
        val imagePostDetails = content as HomePageImagePost

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

        val postUrl = imagePostDetails.postData.submissionImgUrl
        var width = imagePostDetails.postData.submissionImgSizeWidth
        var height = imagePostDetails.postData.submissionImgSizeHeight
        var requestOptions = RequestOptions()
            .fitCenter()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .override(width, height)
        Glide.with(view)
            .load(postUrl)
            .apply(requestOptions)
            .into(postImageView)

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
        Glide.with(view).load(avatarUrl).into(avatarImageView)

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

            SingletonManager.get().ContentManager.favoritePost(imagePostDetails)

            // TODO : Figure out how to mutate this data, and have it be updated
        }

        imgCommentsIcon.setOnClickListener {
            println("Checking comments of ${imagePostDetails.postData.title}")
        }

        imgViewsIcon.setOnClickListener {
            println("Checking details of ${imagePostDetails.postData.title}")
        }
    }

    private fun bindTextPost(view : View, content : IHomePageContent, viewContext: Context) {
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
        Glide.with(view).load(avatarUrl).into(avatarImageView)
    }

    private fun bindMusicPost(view : View, content : IHomePageContent, viewContext: Context) {
        bindImagePost(view, content, viewContext)

        // also bind the music player stuff
        val btnPlay : ImageButton = view.findViewById(R.id.btnPlay)
        btnPlay.setOnClickListener {
            // TODO : Play some music
            println("Gotta play some music!")
        }

        //val seekBar : SeekBar = view.findViewById(R.id.seekBar)
        //seekBar.setOnSeekBarChangeListener
    }

    private fun bindWritingPost(view : View, content: IHomePageContent, viewContext: Context) {
        // TODO : HANDLE AN OPTIONAL
        bindImagePost(view, content, viewContext)
    }

    private fun bindUnknown(view : View, content : IHomePageContent, viewContext: Context) {
        try {
            bindImagePost(view, content, viewContext)
        }
        catch (ex : Exception) {
            val titleTextView : TextView = view.findViewById(R.id.txtTitle)
            titleTextView.text = "Unsupported : " + content.contentId.toString()
        }
    }
}