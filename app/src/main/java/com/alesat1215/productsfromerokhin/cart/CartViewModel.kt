package com.alesat1215.productsfromerokhin.cart

import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.util.CartManager
import javax.inject.Inject

class CartViewModel @Inject constructor(
    override val repository: IProductsRepository
) : CartManager() {
    /** Products in cart */
    fun products() = repository.productsInCart
}