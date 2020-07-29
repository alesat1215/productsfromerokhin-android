package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.ProductInfo
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

/** View model with add & del product from cart */
abstract class CartManager: ViewModel() {
    abstract val productsRepository: IProductsRepository

    fun addProductToCart(productInfo: ProductInfo) {
        viewModelScope.launch {
            productsRepository.addProductToCart(productInfo.asProductInCart())
            Logger.d("Add to cart: ${productInfo.product?.name}")
        }
    }

    fun delProductFromCart(productInfo: ProductInfo) {
        viewModelScope.launch {
            productInfo.inCart.firstOrNull()?.also {
                productsRepository.delProductFromCart(it)
                Logger.d("Del from cart: ${it.name}")
            }
        }
    }
}