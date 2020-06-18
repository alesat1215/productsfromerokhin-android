package com.alesat1215.productsfromerokhin.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.databinding.FragmentMenuBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MenuViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMenuBinding.inflate(inflater, container, false).apply {
        groupsToTabs(groups)
        adapterToProducts(productsMenu)
        lifecycleOwner = this@MenuFragment
        executePendingBindings()
    }.root

    private fun groupsToTabs(tabs: TabLayout) {
        viewModel.groups().observe(viewLifecycleOwner, Observer {
            tabs.removeAllTabs()
            it.forEach { tabs.addTab(tabs.newTab().setText(it.name)) }
        })
    }

    /** Set adapter for list with predicate for dataSet */
    private fun adapterToProducts(list: RecyclerView) =
        viewModel.products().observe(viewLifecycleOwner, Observer {
            list.swapAdapter(BindRVAdapter(it, R.layout.product_item), false)
        })

}