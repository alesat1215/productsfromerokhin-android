package com.alesat1215.productsfromerokhin.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.databinding.FragmentCartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A [CartFragment] subclass of [DaggerFragment].
 * Screen with products in cart
 */
class CartFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<CartViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCartBinding.inflate(inflater, container, false).apply {
        // Add products to list
        adapterToProducts(productsCart)
    }.root

    /** Set adapter for products & restore scroll position */
    private fun adapterToProducts(productsCart: RecyclerView) {
        viewModel.products().observe(viewLifecycleOwner, Observer {
            productsCart.swapAdapter(BindRVAdapter(it, R.layout.product_cart_item), true)
            Log.d("Menu", "Set adapter to products_cart with items count: ${it.count()}")
        })
        // Set scroll position
//        restoreScrollPosition(productsMenu)
    }
}