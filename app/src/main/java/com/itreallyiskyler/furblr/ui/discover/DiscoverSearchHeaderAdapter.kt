package com.itreallyiskyler.furblr.ui.discover

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.enum.SearchKeywordMatching
import com.itreallyiskyler.furblr.enum.SearchOrderBy
import com.itreallyiskyler.furblr.enum.SearchOrderDirection
import com.itreallyiskyler.furblr.enum.SearchRange
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.util.Signal2
import com.itreallyiskyler.furblr.util.ui.AdapterFactory


class DiscoverSearchHeaderAdapter :
    AdapterFactory<Any>(listOf(true)) // true supplied as a dummy value
{
    val searchSignal = Signal2<String, SearchOptions>()
    private var isOptionsCollapsed = true

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.listitem_search_header
    }

    override fun onRenderItem(view: View, content: Any, viewContext: Context) {
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

        fun performSearch() {
            // pull values from the ui
            val keyword = txtSearch.text.toString()
            val sortOrderByIndex = spSortOrderBy.selectedItemPosition
            val sortOrderDirectionIndex = spSortOrderBy2.selectedItemPosition
            val rangeIndex = spSortRange.selectedItemPosition
            val keywordMatchingIndex = spSortKeywords.selectedItemPosition
            val includeGeneral = chRatingGeneral.isChecked
            val includeMature = chRatingMature.isChecked
            val includeAdult = chRatingAdult.isChecked
            val includeArt = chArt.isChecked
            val includeFlash = chFlash.isChecked
            val includeMusic = chMusic.isChecked
            val includePhoto = chPhoto.isChecked
            val includePoetry = chPoetry.isChecked
            val includeStory = chStory.isChecked

            // map the spinner values
            val res = view.resources
            val sortOrderDirection = res.getStringArray(R.array.sortDirectionValues)[sortOrderDirectionIndex]
            val sortOrderBy = res.getStringArray(R.array.sortOrderValues)[sortOrderByIndex]
            val range = res.getStringArray(R.array.sortRangeValues)[rangeIndex]
            val keywordMatching = res.getStringArray(R.array.sortMatchingValues)[keywordMatchingIndex]

            // dispatch the request
            val searchOptions = SearchOptions(
                orderDirection = SearchOrderDirection.fromString(sortOrderDirection),
                orderBy = SearchOrderBy.fromString(sortOrderBy),
                range = SearchRange.fromString(range),
                keywordMatching = SearchKeywordMatching.fromString(keywordMatching),
                includeGeneralContent = includeGeneral,
                includeMatureContent = includeMature,
                includeAdultContent = includeAdult,
                includeArt = includeArt,
                includeFlash = includeFlash,
                includeMusic = includeMusic,
                includePhoto = includePhoto,
                includePoetry = includePoetry,
                includeStory = includeStory
            )

            searchSignal.fire(keyword, searchOptions)
        }
        fun hideKeyboard() {
            val inputMethodManager : InputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        txtSearch.setOnEditorActionListener { _ : View, actionId: Int, _ : KeyEvent? ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                hideKeyboard()
                handled = true
            }
            handled
        }

        btnSearch.setOnClickListener {
            performSearch()
            hideKeyboard()
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