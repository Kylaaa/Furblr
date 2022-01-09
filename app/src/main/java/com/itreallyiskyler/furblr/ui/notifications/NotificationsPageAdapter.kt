package com.itreallyiskyler.furblr.ui.notifications

import android.content.Context
import android.text.Html
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.requests.RequestAvatarUrl
import com.itreallyiskyler.furblr.util.ContentManager
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException


class NotificationsPageAdapter(initialDataSet : List<NotificationsPagePost> = listOf()) :
    RecyclerView.Adapter<NotificationsPageAdapter.ViewHolder>()
{
    // figure out how to pass in nullable data
    private var dataSet : List<NotificationsPagePost> = initialDataSet

    class ViewHolder(val view: View, val viewContext : Context) : RecyclerView.ViewHolder(view)
    {
        private var currentNotification : NotificationsPagePost? = null
        private val loader = Picasso.get()

        // bind data with the UI Elements
        fun bind(notificationDetails : NotificationsPagePost) {
            currentNotification = notificationDetails

            // Define UI Element bindings here
            val contentTextView : TextView = view.findViewById(R.id.txtNotificationContent)
            val postImageView : ImageView = view.findViewById(R.id.imgNotificationPost)
            val notificationKindImageView : ImageView = view.findViewById(R.id.imgNotificationKindIcon)

            // populate the UI elements
            var icon : Int = when (notificationDetails.notification.kind) {
                NotificationId.SubmissionComment.id -> R.drawable.ic_baseline_message_24
                NotificationId.SubmissionCommentReply.id -> R.drawable.ic_baseline_message_24
                NotificationId.Shout.id -> R.drawable.ic_baseline_person_24
                NotificationId.Favorite.id -> R.drawable.ic_baseline_favorite_border_24
                NotificationId.Watch.id -> R.drawable.ic_baseline_eye_24
                else -> throw IndexOutOfBoundsException("Cannot find resource for notification kind")
            }
            notificationKindImageView.setImageResource(icon)

            var sender = notificationDetails.notification.senderId
            var originalPost = notificationDetails.post
            var message : String = when(notificationDetails.notification.kind) {
                NotificationId.SubmissionComment.id ->
                    viewContext.getString(R.string.notification_comment).format(sender, originalPost!!.title)
                NotificationId.SubmissionCommentReply.id ->
                    viewContext.getString(R.string.notification_comment_reply).format(sender, originalPost!!.title)
                NotificationId.Shout.id ->
                    viewContext.getString(R.string.notification_shout).format(sender)
                NotificationId.Favorite.id ->
                    viewContext.getString(R.string.notification_favorited).format(sender, originalPost!!.title)
                NotificationId.Watch.id ->
                    viewContext.getString(R.string.notification_watch).format(sender)
                else -> throw IndexOutOfBoundsException("Cannot find resource for notification kind")
            }
            contentTextView.text = message

            if (notificationDetails.post != null) {
                postImageView.visibility = View.VISIBLE
                val postUrl = notificationDetails.post?.contentsId
                loader.load(postUrl).into(postImageView)
            }
            else {
                postImageView.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId : Int = R.layout.listitem_notification
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postDetails = dataSet[position]
        try {
            holder.bind(postDetails)
        }
        catch (ex : Exception) {
            println("Threw an error binding the view")
        }
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    fun updateData(newDataSet : List<NotificationsPagePost> = listOf())
    {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}