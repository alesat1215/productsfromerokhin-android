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

    private val groupTabs = mutableListOf<TabLayout.Tab>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMenuBinding.inflate(inflater, container, false).apply {
        groupsToTabs(groups)
        adapterToProducts(productsMenu)
        switchGroup(productsMenu)
        lifecycleOwner = this@MenuFragment
        executePendingBindings()
    }.root

    private fun groupsToTabs(tabs: TabLayout) {
        viewModel.groups().observe(viewLifecycleOwner, Observer {
            tabs.removeAllTabs()
            groupTabs.clear()
            it.forEach {
                val tab = tabs.newTab().apply {
                    text = it.name
                    tag = it.id
                }
                groupTabs.add(tab)
                tabs.addTab(tab)
            }
        })
    }

    /** Set adapter for list with predicate for dataSet */
    private fun adapterToProducts(list: RecyclerView) =
        viewModel.products().observe(viewLifecycleOwner, Observer {
            list.swapAdapter(BindRVAdapter(it, R.layout.product_item), false)
        })

    private fun switchGroup(list: RecyclerView) {
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val index = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()
                val product = (recyclerView.adapter as? BindRVAdapter<Product>)?.itemAtIndex(index)
                val group = groups.getTabAt(groups.selectedTabPosition)
                if (group?.tag != product?.group)  {
                    groupTabs.filter { it.tag == product?.group }.firstOrNull()?.select()
                }
            }
        })
    }

}