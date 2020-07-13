package com.alesat1215.productsfromerokhin.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentStartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_start.*
import javax.inject.Inject

/**
 * [StartFragment] subclass of [DaggerFragment].
 * First app screen.
 */
class StartFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    /** Need "by activity" for restore scroll state */
    private val viewModel by activityViewModels<StartViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        /** Bind data to view & setup it */
        FragmentStartBinding.inflate(inflater, container, false).apply {
            /** Set view model to layout */
            viewModel = this@StartFragment.viewModel
            /** Set adapters for products */
            adapterToProducts(productsStart) { it.productDB?.inStart ?: false }
            adapterToProducts(products2Start) { it.productDB?.inStart2 ?: false }
            /** Set lifecycleOwner for LiveData in layout */
            lifecycleOwner = this@StartFragment
            executePendingBindings()
        }.root

    /** Set adapter for list with predicate for dataSet */
    private fun adapterToProducts(list: RecyclerView, predicate: ((Product) -> Boolean)? = null) =
        viewModel.products(predicate).observe(viewLifecycleOwner, Observer {
            list.swapAdapter(BindRVAdapter(it, R.layout.product_start_item), false)
            Log.d("Menu", "Set adapter to products")
        })

    override fun onResume() {
        super.onResume()

        restoreScrollPosition()
    }

    override fun onPause() {
        super.onPause()

        saveScrollPosition()
    }

    /** Restore state from viewModel for lists */
    private fun restoreScrollPosition() {
        viewModel.recyclerViewState[products_start.id]?.also {
            products_start.layoutManager?.onRestoreInstanceState(it)
            Log.d("Start", "Restore state for products")
        }
        viewModel.recyclerViewState[products2_start.id]?.also {
            products2_start.layoutManager?.onRestoreInstanceState(it)
            Log.d("Start", "Restore state for products2")
        }
    }
    /** Save state to viewModel for lists */
    private fun saveScrollPosition() {
        products_start.layoutManager?.onSaveInstanceState()?.also {
            viewModel.recyclerViewState[products_start.id] = it
            Log.d("Start", "Save state for products")
        }
        products2_start.layoutManager?.onSaveInstanceState()?.also {
            viewModel.recyclerViewState[products2_start.id] = it
            Log.d("Start", "Save state for products2")
        }
    }

}


