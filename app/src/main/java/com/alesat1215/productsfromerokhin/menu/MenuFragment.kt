package com.alesat1215.productsfromerokhin.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.databinding.FragmentMenuBinding
import com.alesat1215.productsfromerokhin.start.StartViewModel
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
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
        lifecycleOwner = this@MenuFragment
        groupsToTabs(groups)
    }.root

    private fun groupsToTabs(tabs: TabLayout) {
        viewModel.groups().observe(viewLifecycleOwner, Observer {
            tabs.removeAllTabs()
            it.forEach { tabs.addTab(tabs.newTab().setText(it.name)) }
        })
    }

}