package com.itreallyiskyler.furblr.util.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterFactory<T>(
    protected var dataSet: List<T>,
    ) : RecyclerView.Adapter<AdapterFactory.AdapterFactoryViewHolder<T>>() {

    class AdapterFactoryViewHolder<T>(
        private val view:View,
        private val viewGroup:ViewGroup,
        private val renderFunc: (View, T, Context) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(viewDetails: T) {
            renderFunc(view, viewDetails, viewGroup.context)
        }
    }

    abstract fun onRenderItem(view: View, content : T, viewContext: Context)

    abstract fun getLayoutId(viewType: Int) : Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFactoryViewHolder<T> {
        val resourceId : Int = getLayoutId(viewType)
        val view = LayoutInflater.from(parent.context).inflate(resourceId, parent, false)
        return AdapterFactoryViewHolder(view, parent, ::onRenderItem)
    }

    override fun onBindViewHolder(holder: AdapterFactoryViewHolder<T>, position: Int) {
        val postDetails = dataSet[position]
        holder.bind(postDetails)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newDataSet : List<T> = listOf()) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}