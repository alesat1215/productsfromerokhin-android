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
 * A simple [Fragment] subclass.
 */
class StartFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<StartViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentStartBinding.inflate(inflater, container, false).apply {
            viewModel = this@StartFragment.viewModel
            adapterToList(products) { it.inStart }
            adapterToList(products2) { it.inStart2 }
            lifecycleOwner = this@StartFragment
            executePendingBindings()
        }.root

    private fun adapterToList(list: RecyclerView, predicate: ((Product) -> Boolean)? = null) =
        viewModel.products(predicate).observe(viewLifecycleOwner, Observer {
            list.swapAdapter(BindRVAdapter(it, R.layout.product_item), false)
        })

}


