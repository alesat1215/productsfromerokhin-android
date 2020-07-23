package com.alesat1215.productsfromerokhin.cart

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.util.CartManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(
    override val repository: IProductsRepository
) : CartManager() {
    /** Products in cart */
    fun products() = repository.productsInCart
    /** Sum for order */
    fun totalInCart() = Transformations.map(products()) {
        it.map { it.priceSumInCart() }.sum()
    }
    /** Text for message */
    fun order() = Transformations.map(products()) {
        it.map { it.textForOrder() }.joinToString(separator = ", ${System.lineSeparator()}", postfix = ". ${System.lineSeparator()}")
    }
    /** Delivery info for message */
    fun delivery() = Transformations.map(repository.profile) {
        "${System.lineSeparator()}${System.lineSeparator()}${it.name}${System.lineSeparator()}${it.phone}${System.lineSeparator()}${it.address}"
    }

    fun clearCart() {
        viewModelScope.launch { repository.clearCart() }
    }
}