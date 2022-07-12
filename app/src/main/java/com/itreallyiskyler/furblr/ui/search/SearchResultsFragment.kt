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
import com.itreallyiskyler.furblr.databinding.FragmentSearchResultsBinding
import com.itreallyiskyler.furblr.util.ContentManager

class SearchResultsFragment : Fragment() {

    private lateinit var searchResultsViewModel: SearchResultsViewModel
    private var _binding: FragmentSearchResultsBinding? = null
    private var headerAdapter : SearchResultsHeaderAdapter? = null
    private var resultsAdapter : HomePageAdapter? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        headerAdapter = SearchResultsHeaderAdapter(ContentManager.searchVM.searchQuery.liveData.value ?: "")
        resultsAdapter = HomePageAdapter(ContentManager.searchVM.searchResults.liveData.value ?: listOf())
        searchResultsViewModel =
                ViewModelProvider(this).get(SearchResultsViewModel::class.java)

        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
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