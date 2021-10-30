package com.itreallyiskyler.furblr.ui.home

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.networking.requests.RequestAvatarUrl
import com.itreallyiskyler.furblr.util.ContentManager
import com.squareup.picasso.Picasso

class HomePageAdapter(initialDataSet : List<HomePagePost> = listOf()) :
    RecyclerView.Adapter<HomePageAdapter.ViewHolder>()
{
    private var dataSet : List<HomePagePost> = initialDataSet

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        //
        private var currentPost : HomePagePost? = null

        // Define UI Element bindings here
        private val creatorTextView : TextView = view.findViewById(R.id.txtCreator)
        private val titleTextView : TextView = view.findViewById(R.id.txtTitle)
        private val viewsTextView : TextView = view.findViewById(R.id.txtViews)
        private val favesTextView : TextView = view.findViewById(R.id.txtFaves)
        private val commentsTextView : TextView = view.findViewById(R.id.txtComments)
        private val postImageView : ImageView = view.findViewById(R.id.imgPost)
        private val avatarImageView : ImageView = view.findViewById(R.id.imgAvatar)

        private val loader = Picasso.get()

        fun bind(postDetails : HomePagePost) {
            currentPost = postDetails

            creatorTextView.text = postDetails.postCreator.username
            titleTextView.text = postDetails.postData.title
            viewsTextView.text = postDetails.postData.viewCount.toString()
            favesTextView.text = postDetails.postData.favoriteCount.toString()
            commentsTextView.text = postDetails.postComments.count().toString()
            
            val postUrl = postDetails.postData.contentsId
            loader.load(postUrl).into(postImageView)

            val avatarUrl = RequestAvatarUrl(
                postDetails.postCreator.username,
                postDetails.postCreator.avatarId).getUrl().toString()
            loader.load(avatarUrl).into(avatarImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_home_submission, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postDetails = dataSet.get(position)
        holder.bind(postDetails)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    fun updateData(newDataSet : List<HomePagePost> = listOf())
    {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}