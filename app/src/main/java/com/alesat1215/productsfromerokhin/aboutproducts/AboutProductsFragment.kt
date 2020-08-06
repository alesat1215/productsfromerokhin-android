package com.alesat1215.productsfromerokhin.aboutproducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.MainActivity
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.AboutProducts
import com.alesat1215.productsfromerokhin.databinding.FragmentAboutProductsBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * [AboutProductsFragment] subclass of [DaggerFragment].
 * Screen with about products info
 */
class AboutProductsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AboutProductsViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentAboutProductsBinding.inflate(inflater, container, false).apply {
//            viewModel = this@AboutProductsFragment.viewModel
            // Set adapters to lists
            aboutProductsList.adapter = adapterToAboutProducts(R.layout.about_products_item) { it.img.isEmpty() }
            aboutProductsList2.adapter = adapterToAboutProducts(R.layout.about_products_item2) { it.img.isNotEmpty() }
            // Set lifecycleOwner for LiveData in layout
            lifecycleOwner = this@AboutProductsFragment
            executePendingBindings()
    }.root

    private fun adapterToAboutProducts(
        vhLayout: Int,
        predicate: ((AboutProducts) -> Boolean)
    ): BindRVAdapter<AboutProducts> {
        val adapter = BindRVAdapter<AboutProducts>(vhLayout)
        viewModel.aboutProducts(predicate).observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Logger.d("Set list to adapter for about products: ${it.count()}")
        })
        Logger.d("Set adapter to about products")
        return adapter
    }

    override fun onStart() {
        super.onStart()
        setupBackButton(true)
    }

    override fun onStop() {
        super.onStop()
        setupBackButton(false)
    }

    private fun setupBackButton(enabled: Boolean) {
        (activity as? MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
        (activity as? MainActivity)?.supportActionBar?.setDisplayShowHomeEnabled(enabled)
        Logger.d("Show back button: $enabled")
    }

}