package com.alesat1215.productsfromerokhin.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {

    /** Scroll position for products_menu */
    var scrollPosition = 0

    fun products() = repository.products()

    fun groups() = repository.groups()

    fun addProductToCart(product: Product) {
        Log.d("Menu", product.name)
    }

    fun delProductFromCart(product: Product) {
        Log.d("Menu", product.name)
    }
}