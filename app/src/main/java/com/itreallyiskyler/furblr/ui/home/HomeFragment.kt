package com.itreallyiskyler.furblr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.databinding.FragmentHomeBinding
import com.itreallyiskyler.furblr.util.ContentManager
import javax.sql.DataSource

class HomeFragment : Fragment() {

    private var homeViewModel: HomeViewModel = HomeViewModel()
    private var _binding: FragmentHomeBinding? = null
    private var adapter : HomePageAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding?.homeViewModel = homeViewModel
        adapter = HomePageAdapter()

        homeViewModel.posts.liveData.observe(viewLifecycleOwner, {
            it?.let {
                // TODO : FIGURE OUT WHY DATA IS DOUBLING
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