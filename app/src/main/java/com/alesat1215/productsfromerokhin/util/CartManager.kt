package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

/** View model with add & del product from cart */
abstract class CartManager: ViewModel() {
    abstract val productsRepository: IProductsRepository

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            productsRepository.addProductToCart(product.asProductInCart())
            Logger.d("Add to cart: ${product.productDB?.name}")
        }
    }

    fun delProductFromCart(product: Product) {
        viewModelScope.launch {
            product.inCart.firstOrNull()?.also {
                productsRepository.delProductFromCart(it)
                Logger.d("Del from cart: ${it.name}")
            }
        }
    }
}