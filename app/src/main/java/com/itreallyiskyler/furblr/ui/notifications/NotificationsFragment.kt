package com.itreallyiskyler.furblr.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.databinding.FragmentNotificationsBinding
import com.itreallyiskyler.furblr.managers.SingletonManager
import okhttp3.internal.toImmutableList
import kotlin.concurrent.thread

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private var adapter : NotificationsPageAdapter? = null
    private val MARK_AS_READ_DELAY : Long = 5000
    private var shouldMarkAsRead : Boolean = false


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val notesVM = SingletonManager.get().ContentManager.notesVM
        _binding?.notificationsViewModel = notesVM
        adapter = NotificationsPageAdapter(notesVM.notes.liveData.value ?: listOf())

        notesVM.notes.liveData.observe(viewLifecycleOwner, {
            it?.let {
                adapter?.updateData(it)
            }
        })
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNotesList : RecyclerView = view.findViewById(R.id.rvNotesList)
        rvNotesList.adapter = adapter
        rvNotesList.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        shouldMarkAsRead = true

        thread {
            Thread.sleep(MARK_AS_READ_DELAY)

            if (shouldMarkAsRead) {
                val notificationsPagePosts : MutableList<NotificationsPagePost> = mutableListOf()
                val notesVM = SingletonManager.get().ContentManager.notesVM
                val currentNotes = notesVM.notes.liveData.value
                currentNotes?.forEach {
                    val unreadNotifications = it.notifications.filter { it.hasBeenSeen == false }
                    unreadNotifications.forEach { it.hasBeenSeen = true }
                    val containsUnreadNotes = unreadNotifications.size > 0
                    if (containsUnreadNotes) {
                        notificationsPagePosts.add(it)
                    }
                }
                notesVM.updateNotifications(notificationsPagePosts.toImmutableList())
                SingletonManager.get().ContentManager.markNotificationsAsRead()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        shouldMarkAsRead = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}