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
        // Return empty string for empty profile
        if (it.name.isEmpty() and it.phone.isEmpty() and it.address.isEmpty()) return@map ""
        // Build delivery info from not empty profile
        val separator = System.lineSeparator()
        var result = ""
        result += separator + separator + it.name
        result += (if (result.last().toString() != separator && it.phone.isNotEmpty()) separator else "") + it.phone
        result += (if (result.last().toString() != separator && it.address.isNotEmpty()) separator else "") + it.address
        result
    }

    fun clearCart() {
        viewModelScope.launch { repository.clearCart() }
    }
}