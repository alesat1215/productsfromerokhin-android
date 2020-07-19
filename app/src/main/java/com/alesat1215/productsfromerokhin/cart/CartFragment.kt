package com.alesat1215.productsfromerokhin.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.local.Product
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
        // Set view model to layout
        viewModel = this@CartFragment.viewModel
        // Set fragment to layout
        fragment = this@CartFragment
        // Set adapter to products
        productsCart.adapter = adapterToProducts()
        // Set lifecycleOwner for LiveData in layout
        lifecycleOwner = this@CartFragment
        executePendingBindings()
    }.root

    /** @return adapter for products & set data to it */
    private fun adapterToProducts(): BindRVAdapter<Product> {
        val adapter = BindRVAdapter<Product>(R.layout.menu_item, viewModel)
        viewModel.products().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Log.d("Cart", "Set list to adapter: ${it.count()}")
        })
        Log.d("Cart", "Set adapter to products_cart")
        return adapter
    }

    fun confirm() {
//        val intent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, "textMessage")
//            type = "text/plain"
//        }
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            `package` = "com.whatsapp"
            data = Uri.parse("https://api.whatsapp.com/send?phone=79021228236&text=textMessage")
        }
        val chooser: Intent = Intent.createChooser(intent, "title")
        activity?.packageManager?.also {
            intent.resolveActivity(it)?.also {
                startActivity(chooser)
                Log.d("Cart", "Confirm")
            }
        }
    }

    private fun sendViaWhatsApp() {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            `package` = "com.whatsapp"
            data = Uri.parse("https://api.whatsapp.com/send?phone=79021228236&text=textMessage")
        }
        val chooser: Intent = Intent.createChooser(intent, "title")
        activity?.packageManager?.also {
            intent.resolveActivity(it)?.also {
                startActivity(chooser)
                Log.d("Cart", "Confirm")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Cart", "onActivityResult, requestCode: ${requestCode}, resultCode: ${resultCode}")
    }
}