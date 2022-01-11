package com.itreallyiskyler.furblr.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.databinding.FragmentNotificationsBinding
import com.itreallyiskyler.furblr.util.ContentManager

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private var adapter : NotificationsPageAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding?.notificationsViewModel = ContentManager.notesVM
        adapter = NotificationsPageAdapter(ContentManager.notesVM.notes.liveData.value ?: listOf())

        ContentManager.notesVM.notes.liveData.observe(viewLifecycleOwner, {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}