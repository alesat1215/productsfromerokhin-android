package com.alesat1215.productsfromerokhin.util

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/** Adapter for ViewPager2.
 * Set item of dataSet & last position marker to fragment arguments
 * */
class ViewPagerAdapter<T : Parcelable>(
    fragment: Fragment,
    /** Fragment factory for createFragment method */
    private val creator: () -> Fragment
) : FragmentStateAdapter(fragment)
{
    private var dataSet: List<T> = emptyList()

    /** Update dataSet & notify about changes */
    fun setData(dataSet: List<T>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.count()

    override fun createFragment(position: Int) =
        creator().apply { arguments = Bundle().apply {
            // Set data item
            putParcelable(ITEM, dataSet.getOrNull(position))
            // Set marker of last position
            putBoolean(IS_LAST, dataSet.isEmpty() || position == dataSet.count() - 1)
        } }

    /** Keys for arguments */
    companion object {
        const val ITEM = "ITEM"
        const val IS_LAST = "IS_LAST"
    }
}