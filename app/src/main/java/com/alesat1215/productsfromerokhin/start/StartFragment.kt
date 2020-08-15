package com.alesat1215.productsfromerokhin.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.ProductInfo
import com.alesat1215.productsfromerokhin.databinding.FragmentStartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
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
        // Bind data to view & setup it
        FragmentStartBinding.inflate(inflater, container, false).apply {
            // Set view model to layout
            viewModel = this@StartFragment.viewModel
            // Set adapters to products
            productsStart.adapter = adapterToProducts { it.product.inStart }
            products2Start.adapter = adapterToProducts { it.product.inStart2 }
            // Set lifecycleOwner for LiveData in layout
            lifecycleOwner = this@StartFragment
            executePendingBindings()
        }.root

    /** Create adapter for list with predicate for dataSet */
    private fun adapterToProducts(predicate: ((ProductInfo) -> Boolean)? = null): BindRVAdapter<ProductInfo> {
        val adapter = BindRVAdapter<ProductInfo>(R.layout.start_item, viewModel)
        viewModel.products(predicate).observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Logger.d("Set list to adapter for products in start: ${it.count()}")
        })
        Logger.d("Set adapter to products in start")
        return adapter
    }

    override fun onResume() {
        super.onResume()

        restoreScrollPosition(products_start)
        restoreScrollPosition(products2_start)
    }

    override fun onPause() {
        super.onPause()

        saveScrollPosition(products_start)
        saveScrollPosition(products2_start)
    }

    /** Restore scroll position from viewModel for lists */
    private fun restoreScrollPosition(list: RecyclerView) {
        list.post {
            val position = viewModel.scrollPosition[list.id] ?: 0
            list.smoothScrollBy(position, 0)
            Logger.d("Restore scroll position for id: ${list.id}, position: $position")
        }
    }
    /** Save scroll position to viewModel for lists */
    private fun saveScrollPosition(list: RecyclerView) {
        val position = list.computeHorizontalScrollOffset()
        viewModel.scrollPosition[list.id] = position
        Logger.d("Save scroll position for id: ${list.id}, position: $position")
    }

}


