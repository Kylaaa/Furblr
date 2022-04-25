package com.itreallyiskyler.furblr.util.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterFactory<T>(
    private val dataSet: List<T>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<AdapterFactory.ViewHolder<T>>() {

    class ViewHolder<T>(
        private val view:View,
        private val renderFunc: (View, T) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(viewDetails: T) {
            renderFunc(view, viewDetails)
        }
    }

    abstract fun onRenderItem(view: View, content : T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view, ::onRenderItem)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val postDetails = dataSet[position]
        holder.bind(postDetails)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }
}