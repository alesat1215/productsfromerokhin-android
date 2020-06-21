package com.alesat1215.productsfromerokhin.menu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentMenuBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_menu.*
import java.util.function.BiPredicate
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MenuViewModel> { viewModelFactory }
    /** Local copy of tabs. For filter by group id & change selected */
    private val groupTabs = mutableListOf<TabLayout.Tab>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMenuBinding.inflate(inflater, container, false).apply {
        groupsToTabs(groups)
        scrollToProductWithGroup(groups)
        adapterToProducts(productsMenu)
        switchGroup(productsMenu)
        lifecycleOwner = this@MenuFragment
        executePendingBindings()
    }.root

    /** Setup tabs with groups & save local copy */
    private fun groupsToTabs(tabs: TabLayout) {
        viewModel.groups().observe(viewLifecycleOwner, Observer {
            /** Clear tabs from view & local copy */
            tabs.removeAllTabs()
            groupTabs.clear()
            /** Set tabs to view & local copy */
            it.forEach {
                val tab = tabs.newTab().apply {
                    text = it.name
                    tag = it.id
                }
                // Save to local copy
                groupTabs.add(tab)
                // Set tabs to bar
                tabs.addTab(tab)
            }
        })
    }

    /** Set adapter for products */
    private fun adapterToProducts(list: RecyclerView) =
        viewModel.products().observe(viewLifecycleOwner, Observer {
            list.swapAdapter(BindRVAdapter(it, R.layout.product_item), false)
        })

    /** Switch tabs to for group of current product */
    private fun switchGroup(list: RecyclerView) {
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            /** Current group */
            private var group = groups?.getTabAt(groups?.selectedTabPosition ?: 0)
            /** Check group for visible product & switch it if needed */
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                /** Visible position */
                val index = (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                /** Current product at position */
                val product = (recyclerView.adapter as? BindRVAdapter<Product>)?.itemAtIndex(index)
                /** Switch group if needed */
                if (group?.tag != product?.group)  {
                    /** Found group with id in local copy of tabs, update current group & select it */
                    group = groupTabs.filter { it.tag == product?.group }.firstOrNull()
                    group?.select()
                }
            }
        })
    }

    private fun scrollToProductWithGroup(groups: TabLayout) {
        groups.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val index = viewModel.products().value?.indexOfFirst { it.group == tab?.tag }
                (products_menu.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(index ?: 0, 0)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        })
    }

}