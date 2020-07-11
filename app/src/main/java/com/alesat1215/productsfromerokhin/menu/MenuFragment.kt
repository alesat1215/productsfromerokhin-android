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
import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentMenuBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
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
    /** Local copy of tabs. For filter by group id & change selected */
    private val groupTabs = mutableListOf<TabLayout.Tab>()
    /** For scrolling to product only for click on tab */
    private var tabSelected = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMenuBinding.inflate(inflater, container, false).apply {
        /** Add groups to tabs, products to list */
        bindGroupsAndProducts(groupsMenu, productsMenu)
        /** Add scroll to product when group selected */
        onTabSelected(groupsMenu)
        /** Add switch group when scroll & save position */
        onScroll(productsMenu)
        /** Set lifecycleOwner for LiveData in layout */
        lifecycleOwner = this@MenuFragment
        executePendingBindings()
    }.root

    /** Subscribe to events from groups. After add groups to tabs, subscribe for single event from products &
     * bind products to list
     * */
    private fun bindGroupsAndProducts(groups: TabLayout, productsMenu: RecyclerView) {
        // Subscribe to events from group
        viewModel.groups().observe(viewLifecycleOwner, Observer {
            // Add groups to tabs
            groupsToTabs(groups, it)
            // Add products to list
            val products = viewModel.products()
            products.observe(viewLifecycleOwner, Observer {
                adapterToProducts(productsMenu, it)
                // Unsubscribe from events for products
                products.removeObservers(viewLifecycleOwner)
            })
        })
    }

    /** Setup tabs with groups & save local copy */
    private fun groupsToTabs(groups: TabLayout, data: List<Group>) {
        /** Clear tabs from view & local copy */
        groups.removeAllTabs()
        groupTabs.clear()
        /** Set tabs to view & local copy */
        data.forEach {
            val tab = groups.newTab().apply {
                text = it.name
                tag = it.id
            }
            // Save to local copy
            groupTabs.add(tab)
            // Set tabs to bar
            groups.addTab(tab)
        }
        Log.d("Menu", "Add groups to tabs: ${groupTabs.count()}")
    }
    /** Set adapter for products & restore scroll position */
    private fun adapterToProducts(productsMenu: RecyclerView, data: List<Product>) {
        productsMenu.swapAdapter(BindRVAdapter(data, R.layout.product_menu_item, viewModel), true)
        Log.d("Menu", "Set adapter to products_menu with items count: ${data.count()}")
        // Set scroll position
        restoreScrollPosition(productsMenu)
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
                val index = (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
                /** Current product at position */
                val product = (recyclerView.adapter as? BindRVAdapter<Product>)?.itemAtIndex(index)
                /** Switch group if needed */
                if (group?.tag != product?.group)  {
                    /** Found group with id in local copy of tabs, update current group & select it */
                    group = groupTabs.filter { it.tag == product?.group }.firstOrNull()
                    /** Disable scrolling in tab select listener */
                    tabSelected = false
                    /** Select tab */
                    group?.select()
                    Log.d("Menu", "Change group id to: ${group?.tag}")
                    /** Enable scrolling in tab select listener */
                    tabSelected = true
                    /** Fix scroll position for first item.
                     * Need for correct scroll position when groups are updates in this screen */
                    if (groups_menu?.selectedTabPosition == 0) groups_menu?.scrollTo(0, 0)
                }
            }

            /** Save scroll position */
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // Save position when scroll stop
                if (newState == RecyclerView.SCROLL_STATE_IDLE) saveScrollPosition(recyclerView)
            }
        })
    }

    /** Scroll to first product with current group id. Only for click on tab event */
    private fun onTabSelected(groups: TabLayout) {
        groups.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                /** If click on tab */
                if (tabSelected) {
                    /** Find first product with group id */
                    val position = viewModel.products().value?.indexOfFirst { it.group == tab?.tag } ?: 0
                    /** Scroll to position */
                    products_menu.layoutManager?.startSmoothScroll(object : LinearSmoothScroller(context) {
                        // Scroll item in top
                        override fun getVerticalSnapPreference() = SNAP_TO_START
                        // Set scroll position
                    }.apply { targetPosition = position })
                    Log.d("Menu", "For tab click scroll to position: ${position}, group: ${tab?.tag}")
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        })
    }

    /** Save state to viewModel for list */
    private fun saveScrollPosition(list: RecyclerView) {
        viewModel.scrollPosition = list.computeVerticalScrollOffset()
        Log.d("Menu", "Save scroll position for products_menu: ${viewModel.scrollPosition}")
    }
    /** Restore state from viewModel for list */
    private fun restoreScrollPosition(list: RecyclerView) {
        list.post { list.smoothScrollBy(0, viewModel.scrollPosition) }
        Log.d("Menu", "Restore scroll position for products_menu: ${viewModel.scrollPosition}")
    }

}