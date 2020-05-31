package com.alesat1215.productsfromerokhin.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class BindViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

class BindRVAdapter<T>(private val dataSet: List<T>, private val vhLayout: Int): RecyclerView.Adapter<BindViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), vhLayout, parent, false)
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        holder.binding.setVariable(BR.product, dataSet[position])
        holder.binding.executePendingBindings()
    }
}