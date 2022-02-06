package com.itreallyiskyler.furblr.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.SearchMode
import com.itreallyiskyler.furblr.enum.SearchOrderBy
import com.itreallyiskyler.furblr.enum.SearchOrderDirection
import com.itreallyiskyler.furblr.enum.SearchRange
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.util.ContentManager


class DiscoverSearchHeaderAdapter() :
    RecyclerView.Adapter<DiscoverSearchHeaderAdapter.ViewHolder>()
{
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        var isOptionsCollapsed = true

        // bind data with the UI Elements
        fun bind() {
            val txtSearch : TextInputEditText = view.findViewById(R.id.txtSearch)
            val btnSearch : ImageButton = view.findViewById(R.id.btnSearch)
            val btnShowOptions : ImageButton = view.findViewById(R.id.btnShowOptions)
            val layoutOptions : ConstraintLayout = view.findViewById(R.id.layoutOptions)
            val spSortOrderBy : Spinner = view.findViewById(R.id.spSortOrderBy)
            val spSortOrderBy2 : Spinner = view.findViewById(R.id.spSortOrderBy2)
            val spSortRange : Spinner = view.findViewById(R.id.spSortRange)
            val spSortKeywords : Spinner = view.findViewById(R.id.spSortKeywords)
            val chRatingGeneral : Chip = view.findViewById(R.id.chRatingGeneral)
            val chRatingMature : Chip = view.findViewById(R.id.chRatingMature)
            val chRatingAdult : Chip = view.findViewById(R.id.chRatingAdult)
            val chArt : Chip = view.findViewById(R.id.chPostArt)
            val chFlash : Chip = view.findViewById(R.id.chPostFlash)
            val chMusic : Chip = view.findViewById(R.id.chPostMusic)
            val chPhoto : Chip = view.findViewById(R.id.chPostPhoto)
            val chPoetry : Chip = view.findViewById(R.id.chPostPoetry)
            val chStory : Chip = view.findViewById(R.id.chPostStory)

            btnSearch.setOnClickListener {
                // pull values from the ui
                val keyword = txtSearch.text.toString()
                val sortOrderBy = spSortOrderBy.selectedItem
                val orderBy = spSortOrderBy2.selectedItem
                val range = spSortRange.selectedItem
                val keywords = spSortKeywords.selectedItem
                val includeGeneral = chRatingGeneral.isChecked
                val includeMature = chRatingMature.isChecked
                val includeAdult = chRatingAdult.isChecked
                val includeArt = chArt.isChecked
                val includeFlash = chFlash.isChecked
                val includeMusic = chMusic.isChecked
                val includePhoto = chPhoto.isChecked
                val includePoetry = chPoetry.isChecked
                val includeStory = chStory.isChecked

                // dispatch the request
                ContentManager.fetchSearchPage(keyword, SearchOptions(
                    //orderDirection = SearchOrderDirection.Descending,
                    //orderBy = SearchOrderBy.Relevancy,
                    //range = SearchRange.All,
                    //mode = SearchMode.All,
                    includeGeneralContent = includeGeneral,
                    includeMatureContent = includeMature,
                    includeAdultContent = includeAdult,
                    includeArt = includeArt,
                    includeFlash = includeFlash,
                    includeMusic = includeMusic,
                    includePhoto = includePhoto,
                    includePoetry = includePoetry,
                    includeStory = includeStory
                ))
            }

            btnShowOptions.setOnClickListener {
                // show / hide all the search  options
                layoutOptions.visibility = if (isOptionsCollapsed) View.VISIBLE else View.GONE

                // flip the image in the button
                val imgResource = if (isOptionsCollapsed) R.drawable.ic_baseline_expand_more_24 else R.drawable.ic_baseline_expand_less_24
                btnShowOptions.setImageResource(imgResource)

                // flip the bool for next time
                isOptionsCollapsed = !isOptionsCollapsed
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.listitem_search_header, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 1
    }
}