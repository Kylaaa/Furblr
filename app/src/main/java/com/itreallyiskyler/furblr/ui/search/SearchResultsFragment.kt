package com.itreallyiskyler.furblr.ui.search

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

class SearchResultsFragment(val searchQuery : String) : Fragment() {

    private lateinit var searchResultsViewModel: SearchResultsViewModel
    private var _binding: FragmentDiscoverBinding? = null
    private var headerAdapter : SearchResultsHeaderAdapter? = null
    private var resultsAdapter : HomePageAdapter? = null

    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        headerAdapter = SearchResultsHeaderAdapter(searchQuery)
        resultsAdapter = HomePageAdapter(ContentManager.homeVM.posts.liveData.value ?: listOf())
        searchResultsViewModel =
                ViewModelProvider(this).get(SearchResultsViewModel::class.java)

        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvSearchResults : RecyclerView = view.findViewById(R.id.rvSearchResults)
        rvSearchResults.adapter = ConcatAdapter(headerAdapter, resultsAdapter)
        rvSearchResults.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}