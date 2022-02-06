package com.itreallyiskyler.furblr.ui.discover

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.SearchMode
import com.itreallyiskyler.furblr.enum.SearchOrderBy
import com.itreallyiskyler.furblr.enum.SearchOrderDirection
import com.itreallyiskyler.furblr.enum.SearchRange
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.ui.home.HomePageAdapter
import com.itreallyiskyler.furblr.ui.home.HomePageDisplayOptions
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.ContentManager
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList


class DiscoverSectionAdapter(private val viewContext : Context) :
    RecyclerView.Adapter<DiscoverSectionAdapter.ViewHolder>()
{
    // TODO : replace this with content populated from the FA home page
    val sectionData : List<Pair<String, SynchronizedLiveDataList<IHomePageContent>>> = listOf(
        Pair(viewContext.getString(R.string.ui_discover_section_submissions), ContentManager.homeVM.posts),
        Pair(viewContext.getString(R.string.ui_discover_section_writing), ContentManager.homeVM.posts),
        Pair(viewContext.getString(R.string.ui_discover_section_music), ContentManager.homeVM.posts),
        Pair(viewContext.getString(R.string.ui_discover_section_crafting), ContentManager.homeVM.posts),
    )

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        lateinit var viewContext: Context

        // bind data with the UI Elements
        fun bind(sectionTitle : String, data : SynchronizedLiveDataList<IHomePageContent>) {
            val txtTitle : TextView = view.findViewById(R.id.txtDiscoverSectionTitle)
            val contentView : RecyclerView = view.findViewById(R.id.rvDiscoverView)

            txtTitle.text = sectionTitle
            contentView.adapter = HomePageAdapter(
                data.liveData.value?:listOf(),
                HomePageDisplayOptions(showDetails = false, fitHorizontal = false)
            )
            contentView.layoutManager = LinearLayoutManager(
                viewContext,
                LinearLayoutManager.HORIZONTAL,
                false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.listitem_search_horizontal_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data : Pair<String, SynchronizedLiveDataList<IHomePageContent>> = sectionData[position]
        holder.viewContext = viewContext
        holder.bind(data.first, data.second)
    }

    override fun getItemCount(): Int {
        return sectionData.size
    }
}