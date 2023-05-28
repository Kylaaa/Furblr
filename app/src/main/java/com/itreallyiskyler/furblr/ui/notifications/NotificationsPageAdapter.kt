package com.itreallyiskyler.furblr.ui.notifications

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.NotificationId
import com.itreallyiskyler.furblr.persistence.entities.Notification
import com.itreallyiskyler.furblr.util.DateFormatter
import com.itreallyiskyler.furblr.util.ui.AdapterFactory
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.lang.IndexOutOfBoundsException


class NotificationsPageAdapter(initialDataSet : List<NotificationsPagePost> = listOf()) :
    AdapterFactory<NotificationsPagePost>(initialDataSet)
{
    private val loader = Picasso.get()

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.listitem_day_of_notifications
    }

    override fun onRenderItem(view: View, content: NotificationsPagePost, viewContext: Context) {
        val dateTitle: TextView = view.findViewById(R.id.txtDateTitle)
        val notesLayout: LinearLayout = view.findViewById(R.id.frNotifications)

        // TODO : PARSE THIS DATE TO A HUMAN READABLE DATE
        dateTitle.text = DateFormatter.convertToReadableDate(content.date, viewContext)

        // add all of the notifications to the view
        notesLayout.removeAllViews()
        val viewInflater = LayoutInflater.from(viewContext)
        content.notifications.forEach {
            try {
                val layout = viewInflater.inflate(
                    R.layout.listitem_notification,
                    null
                ) as ConstraintLayout

                val originalPost : com.itreallyiskyler.furblr.persistence.entities.View? = content.post[it]
                inflateNotification(layout, viewContext, it, originalPost)

                notesLayout.addView(layout)
            }
            catch (ex : Exception) {
                println("Failed to bind notification : $ex")
            }
        }
    }

    private fun inflateNotification(
        targetView : View,
        viewContext : Context,
        note : Notification,
        originalPost : com.itreallyiskyler.furblr.persistence.entities.View? = null) {
        // Define UI Element bindings here
        val contentTextView : TextView = targetView.findViewById(R.id.txtNotificationContent)
        val postImageView : ImageView = targetView.findViewById(R.id.imgNotificationPost)
        val notificationKindImageView : ImageView = targetView.findViewById(R.id.imgNotificationKindIcon)

        // populate the UI elements
        var icon : Int = when (note.kind) {
            NotificationId.SubmissionComment.id -> R.drawable.ic_baseline_message_24
            NotificationId.SubmissionCommentReply.id -> R.drawable.ic_baseline_message_24
            NotificationId.Shout.id -> R.drawable.ic_baseline_person_24
            NotificationId.Favorite.id -> R.drawable.ic_baseline_favorite_border_24
            NotificationId.Watch.id -> R.drawable.ic_baseline_eye_24
            else -> throw IndexOutOfBoundsException("Cannot find resource for notification kind")
        }
        notificationKindImageView.setImageResource(icon)
        if (note.hasBeenSeen == false) {
            notificationKindImageView.imageTintList =
                ColorStateList.valueOf(viewContext.getColor(R.color.design_default_color_secondary))
        }

        var sender = note.senderId
        var message : String = when(note.kind) {
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

        if (originalPost != null) {
            postImageView.visibility = View.VISIBLE
            val postUrl = originalPost?.submissionImgUrl
            loader.load(postUrl).into(postImageView)
        }
        else {
            postImageView.visibility = View.GONE
        }
    }

}