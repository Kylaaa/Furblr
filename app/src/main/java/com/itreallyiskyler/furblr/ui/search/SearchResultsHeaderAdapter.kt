package com.itreallyiskyler.furblr.ui.search


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R


class SearchResultsHeaderAdapter(val searchQuery: String) :
    RecyclerView.Adapter<SearchResultsHeaderAdapter.ViewHolder>()
{
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        // bind data with the UI Elements
        fun bind(searchQuery : String) {
            val lblSearchResults : TextView = view.findViewById(R.id.lblTitleSearch2)

            lblSearchResults.text = searchQuery
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.listitem_search_result_header, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchQuery)
    }

    override fun getItemCount(): Int {
        return 1
    }
}