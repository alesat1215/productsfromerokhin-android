package com.alesat1215.productsfromerokhin.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentStartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_start.*
import javax.inject.Inject

/**
 * [StartFragment] subclass DaggerFragment.
 * First app screen.
 */
class StartFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    /** Need "by activity" for restore scroll state */
    private val viewModel by activityViewModels<StartViewModel> { viewModelFactory }
    /** Key for restore state of products list */
    private val productsState = "products"
    /** Key for restore state of products2 list */
    private val products2State = "products2"

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

    override fun onPause() {
        super.onPause()

        saveScrollPosition()
    }

    override fun onResume() {
        super.onResume()

        restoreScrollPosition()
    }

    /** Save state to viewModel for products & products2 */
    private fun saveScrollPosition() {
        products.layoutManager?.onSaveInstanceState()?.also {
            viewModel.recyclerViewState[productsState] = it
            Log.d("Scroll", "Save state for ${productsState}")
        }
        products2.layoutManager?.onSaveInstanceState()?.also {
            viewModel.recyclerViewState[products2State] = it
            Log.d("Scroll", "Save state for ${products2State}")
        }
    }
    /** Restore state from viewModel for products & products2 */
    private fun restoreScrollPosition() {
        viewModel.recyclerViewState[productsState]?.also {
            products.layoutManager?.onRestoreInstanceState(it)
            Log.d("Scroll", "Restore state for ${productsState}")
        }
        viewModel.recyclerViewState[products2State]?.also {
            products2.layoutManager?.onRestoreInstanceState(it)
            Log.d("Scroll", "Restore state for ${products2State}")
        }
    }

}


