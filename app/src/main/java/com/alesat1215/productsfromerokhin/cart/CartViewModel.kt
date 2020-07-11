package com.alesat1215.productsfromerokhin.cart

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {
    /** Products in cart */
    fun products() = repository.productsInCart
}