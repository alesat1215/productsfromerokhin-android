package com.alesat1215.productsfromerokhin.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentMenuBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import com.alesat1215.productsfromerokhin.util.tabWithText
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

/**
 * [MenuFragment] subclass of [DaggerFragment].
 * Screen with groups & products
 */
class MenuFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    /** Need "by activity" for restore scroll state */
    private val viewModel by activityViewModels<MenuViewModel> { viewModelFactory }
    /** For scrolling to product only for click on tab */
    private var tabSelected = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMenuBinding.inflate(inflater, container, false).apply {
        // Add groups to tabs
        groupsToTabs(groupsMenu)
        // Set adapter to products
        productsMenu.adapter = adapterToProducts()
        /** Add scroll to product when group selected */
        onTabSelected(groupsMenu)
        /** Add switch group when scroll & save position */
        onScroll(productsMenu)
        /** Set lifecycleOwner for LiveData in layout */
        lifecycleOwner = this@MenuFragment
        executePendingBindings()
    }.root

    override fun onResume() {
        super.onResume()

        restoreScrollPosition(products_menu)
    }

    override fun onPause() {
        super.onPause()

        saveScrollPosition(products_menu)
    }

    /** Set groups to tabs */
    private fun groupsToTabs(groups: TabLayout) {
        viewModel.groups().observe(viewLifecycleOwner, Observer {
            // Save selected tab position
            val selected = groups.selectedTabPosition
            // Disable scrolling in tab select listener
            tabSelected = false
            // Clear tabs from view
            groups.removeAllTabs()
            // Set tabs to view
            it.forEach {
                val tab = groups.newTab().apply {
                    text = it.name
                }
                // Set tabs to bar
                groups.addTab(tab)
            }
            Log.d("Menu", "Add groups to tabs: ${groups.tabCount}")
            // Enable scrolling in tab select listener
            tabSelected = true
            // Restore selected tab
            selectGroup(groups.getTabAt(selected), groups)
        })
    }
    /** Create adapter for products & set data to it */
    private fun adapterToProducts(): BindRVAdapter<Product> {
        val adapter = BindRVAdapter<Product>(R.layout.menu_item, viewModel)
        viewModel.products().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Log.d("Menu", "Set list to adapter for products_menu: ${it.count()}")
        })
        Log.d("Menu", "Set adapter to products_menu")
        return adapter
    }

    /** Switch tabs to group of current product & save scroll position */
    private fun onScroll(productsMenu: RecyclerView) {
        productsMenu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            /** Current group */
            private var group = groups_menu?.getTabAt(groups_menu?.selectedTabPosition ?: 0)
            /** Check group for visible product & switch it if needed */
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                /** Visible position */
                val index = (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition() ?: 0
                /** Current product at position */
                val product = (recyclerView.adapter as? BindRVAdapter<Product>)?.getItem(index)
                // Switch group if needed
                if (group?.text != product?.productDB?.group)  {
                    // Found tab with text
                    group = groups_menu?.tabWithText(product?.productDB?.group)
                    // Select tab
                    selectGroup(group, groups_menu)
                }
            }
        })
    }

    private fun selectGroup(group: TabLayout.Tab?, groups: TabLayout?) {
        groups?.post {
            // Disable scrolling in tab select listener
            tabSelected = false
            group?.select()
            // Enable scrolling in tab select listener
            tabSelected = true
            Log.d("Menu", "Change group to: ${group?.text}")
        }
    }

    /** Scroll to first product with current group id. Only for click on tab event */
    private fun onTabSelected(groups: TabLayout) {
        groups.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                /** If click on tab */
                if (tabSelected) {
                    /** Find first product with group id */
                    val position = viewModel.products().value?.indexOfFirst { it.productDB?.group == tab?.text } ?: 0
                    /** Scroll to position */
                    products_menu.layoutManager?.startSmoothScroll(object : LinearSmoothScroller(context) {
                        // Scroll item in top
                        override fun getVerticalSnapPreference() = SNAP_TO_START
                        // Set scroll position
                    }.apply { targetPosition = position })
                    Log.d("Menu", "For tab click scroll to position: ${position}, group: ${tab?.text}")
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        })
    }

    /** Save scroll position for products_menu */
    private fun saveScrollPosition(list: RecyclerView) {
        viewModel.scrollPosition = list.computeVerticalScrollOffset()
        Log.d("Menu", "Save scroll position for products_menu: ${viewModel.scrollPosition}")
    }
    /** Restore scroll position for products_menu */
    private fun restoreScrollPosition(list: RecyclerView) {
        list.post { list.smoothScrollBy(0, viewModel.scrollPosition) }
        Log.d("Menu", "Restore scroll position for products_menu: ${viewModel.scrollPosition}")
    }

}