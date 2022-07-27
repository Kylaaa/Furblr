package com.itreallyiskyler.furblr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.databinding.FragmentHomeBinding
import com.itreallyiskyler.furblr.managers.ContentManager

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private var adapter : HomePageAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding?.homeViewModel = ContentManager.homeVM
        adapter = HomePageAdapter(ContentManager.homeVM.posts.liveData.value ?: listOf())

        ContentManager.homeVM.posts.liveData.observe(viewLifecycleOwner, {
            it?.let {
                adapter?.updateData(it)
            }
        })
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvHomeList : RecyclerView = view.findViewById(R.id.rvHomeList)
        rvHomeList.adapter = adapter
        rvHomeList.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}