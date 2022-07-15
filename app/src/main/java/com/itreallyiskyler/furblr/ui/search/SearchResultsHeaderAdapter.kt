package com.itreallyiskyler.furblr.ui.search


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R


class SearchResultsHeaderAdapter(val searchQuery: String, val totalResults : Int) :
    RecyclerView.Adapter<SearchResultsHeaderAdapter.ViewHolder>()
{
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        // bind data with the UI Elements
        fun bind(searchQuery : String, totalResults: Int) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.listitem_search_result_header, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchQuery, totalResults)
    }

    override fun getItemCount(): Int {
        return 1
    }
}