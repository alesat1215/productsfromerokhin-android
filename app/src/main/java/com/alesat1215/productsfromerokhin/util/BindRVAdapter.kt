package com.alesat1215.productsfromerokhin.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/** View holder for data binding */
class BindViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

/** Adapter for binding items from dataSet to vhLayout */
class BindRVAdapter<T>(private val vhLayout: Int, private val viewModel: ViewModel? = null):
    ListAdapter<T, BindViewHolder>(DiffCallback<T>()) {

    /** Binding layout to view holder & return it */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), vhLayout, parent, false)
        )

    /** Set item with data to layout */
    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        holder.binding.setVariable(BR.data, getItem(position))
        holder.binding.setVariable(BR.viewModel, viewModel)
        holder.binding.executePendingBindings()
    }

    // Make get item public
    public override fun getItem(position: Int) = super.getItem(position)
}

/** Diff for BindRVAdapter. Items must be data classes */
class DiffCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}