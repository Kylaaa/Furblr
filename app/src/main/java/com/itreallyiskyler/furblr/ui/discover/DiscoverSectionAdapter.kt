package com.itreallyiskyler.furblr.ui.discover

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.ui.home.IHomePageContent
import com.itreallyiskyler.furblr.util.SynchronizedLiveDataList
import com.itreallyiskyler.furblr.util.ui.AdapterFactory

// Section of horizontal content labeled with a large header
class DiscoverSectionAdapter(
    sectionData : List<Pair<Int, SynchronizedLiveDataList<IHomePageContent>>>) :
    AdapterFactory<Pair<Int, SynchronizedLiveDataList<IHomePageContent>>>(sectionData)
{
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.listitem_search_horizontal_list
    }

    override fun onRenderItem(
        view: View,
        content: Pair<Int, SynchronizedLiveDataList<IHomePageContent>>,
        viewContext: Context
    ) {
        val sectionTitle : String = viewContext.getString(content.first)
        val data : SynchronizedLiveDataList<IHomePageContent> = content.second

        val txtTitle : TextView = view.findViewById(R.id.txtDiscoverSectionTitle)
        val contentView : RecyclerView = view.findViewById(R.id.rvDiscoverView)

        txtTitle.text = sectionTitle
        contentView.layoutManager = LinearLayoutManager(
            viewContext,
            LinearLayoutManager.HORIZONTAL,
            false)
        contentView.adapter = DiscoverPreviewAdapter(data.liveData.value?:listOf())
    }
}