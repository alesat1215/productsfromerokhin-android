package com.alesat1215.productsfromerokhin.util

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter<T : Parcelable>(
    fragment: Fragment,
    private val creator: () -> Fragment
) : FragmentStateAdapter(fragment)
{
    private var dataSet: List<T> = emptyList()

    fun setData(dataSet: List<T>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.count()

    override fun createFragment(position: Int) =
        creator().apply { arguments = Bundle().apply { putParcelable(ARGUMENT_KEY, dataSet.getOrNull(position)) } }

    companion object {
        const val ARGUMENT_KEY = "ARGUMENT_KEY"
    }
}