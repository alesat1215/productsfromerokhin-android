package com.alesat1215.productsfromerokhin.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentStartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * [StartFragment] subclass DaggerFragment.
 * First app screen.
 */
class StartFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<StartViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        /** Bind data to view & setup it */
        FragmentStartBinding.inflate(inflater, container, false).apply {
            /** Set view model to layout */
            viewModel = this@StartFragment.viewModel
            /** Set adapters for products */
            adapterToProducts(products) { it.inStart }
            adapterToProducts(products2) { it.inStart2 }
            /** Set lifecycleOwner for LiveData in layout */
            lifecycleOwner = this@StartFragment
            executePendingBindings()
        }.root

    /** Set adapter for list with predicate for dataSet */
    private fun adapterToProducts(list: RecyclerView, predicate: ((Product) -> Boolean)? = null) =
        viewModel.products(predicate).observe(viewLifecycleOwner, Observer {
            list.swapAdapter(BindRVAdapter(it, R.layout.product_item), false)
        })

}


