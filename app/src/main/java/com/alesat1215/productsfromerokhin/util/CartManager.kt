package com.alesat1215.productsfromerokhin.util

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import kotlinx.coroutines.launch

/** View model with add & del product from cart */
abstract class CartManager: ViewModel() {
    abstract val repository: IProductsRepository

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            repository.addProductToCart(product.productInCart())
            Log.d("Menu", "Add to cart: ${product.productDB?.name}")
        }
    }

    fun delProductFromCart(product: Product) {
        viewModelScope.launch {
            product.inCart.firstOrNull()?.also {
                repository.delProductFromCart(it)
                Log.d("Menu", "Del from cart: ${it.name}")
            }
        }
    }
}