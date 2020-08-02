package com.alesat1215.productsfromerokhin.cart

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IPhoneRepository
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.IProfileRepository
import com.alesat1215.productsfromerokhin.util.CartManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(
    override val productsRepository: IProductsRepository,
    private val profileRepository: IProfileRepository,
    private val phoneRepository: IPhoneRepository
) : CartManager() {
    /** Products in cart */
    val productsInCart by lazy { productsRepository.productsInCart }
    /** Sum for order */
    val totalInCart by lazy {
        Transformations.map(productsInCart) {
            it.map { it.priceSumInCart() }.sum()
        }
    }
    /** Text for message */
    val order by lazy {
        Transformations.map(productsInCart) {
            it.map { it.textForOrder() }.joinToString(separator = ", ${System.lineSeparator()}", postfix = ". ${System.lineSeparator()}")
        }
    }
    /** Delivery info for message */
    val delivery by lazy { Transformations.map(profileRepository.profile) { it?.delivery().orEmpty() } }

    fun clearCart() {
        viewModelScope.launch { productsRepository.clearCart() }
    }

    fun phone() = phoneRepository.phone()
}