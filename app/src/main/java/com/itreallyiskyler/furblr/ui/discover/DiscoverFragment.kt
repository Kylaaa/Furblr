package com.itreallyiskyler.furblr.ui.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itreallyiskyler.furblr.ui.home.HomePageAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.databinding.FragmentDiscoverBinding
import com.itreallyiskyler.furblr.util.ContentManager

class DiscoverFragment : Fragment() {

    private lateinit var dashboardViewModel: DiscoverViewModel
    private var _binding: FragmentDiscoverBinding? = null
    private var searchAdapter : DiscoverSearchHeaderAdapter? = null
    private var discoverAdapter : DiscoverSectionAdapter? = null
    private var adapter : ConcatAdapter? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val viewContext : Context = this.requireContext()

        searchAdapter = DiscoverSearchHeaderAdapter()
        discoverAdapter = DiscoverSectionAdapter(
            viewContext,
            ContentManager.discoverVM.discoverDataSets)
        adapter = ConcatAdapter(searchAdapter, discoverAdapter)
        dashboardViewModel =
                ViewModelProvider(this).get(DiscoverViewModel::class.java)

        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvDiscoverList : RecyclerView = view.findViewById(R.id.rvDiscoverList)
        rvDiscoverList.adapter = adapter
        rvDiscoverList.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}