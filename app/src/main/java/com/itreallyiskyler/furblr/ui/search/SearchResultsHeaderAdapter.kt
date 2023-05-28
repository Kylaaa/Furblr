package com.itreallyiskyler.furblr.ui.search

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.util.ui.AdapterFactory


class SearchResultsHeaderAdapter(val searchQuery: String, val totalResults : Int) :
    AdapterFactory<Any>(listOf(true)) // true supplied as a dummy value
{
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.listitem_search_result_header
    }

    override fun onRenderItem(view: View, content: Any, viewContext: Context) {
        val lblSearchResults : TextView = view.findViewById(R.id.lblTitleSearch)
        val lblTotalResults : TextView = view.findViewById(R.id.lblTitleSearch2)
        val btnBack : ImageButton = view.findViewById(R.id.btnBack)

        lblSearchResults.text = searchQuery
        lblTotalResults.text = view.resources.getString(R.string.search_total_results, totalResults)

        btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }
}