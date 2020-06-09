package com.alesat1215.productsfromerokhin.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

/** View holder for data binding */
class BindViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

/** Adapter for binding items from dataSet to vhLayout */
class BindRVAdapter<T>(private val dataSet: List<T>, private val vhLayout: Int): RecyclerView.Adapter<BindViewHolder>() {

    /** Binding layout to view holder & return it */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), vhLayout, parent, false)
        )

    override fun getItemCount() = dataSet.count()

    /** Set item with data to layout */
    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        holder.binding.setVariable(BR.product, dataSet[position])
        holder.binding.executePendingBindings()
    }
}