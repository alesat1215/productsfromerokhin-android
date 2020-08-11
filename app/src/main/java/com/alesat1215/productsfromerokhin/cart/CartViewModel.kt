package com.alesat1215.productsfromerokhin.cart

import androidx.lifecycle.*
import com.alesat1215.productsfromerokhin.data.IContactsRepository
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.IProfileRepository
import com.alesat1215.productsfromerokhin.util.CartManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(
    override val productsRepository: IProductsRepository,
    private val profileRepository: IProfileRepository,
    private val contactsRepository: IContactsRepository
) : CartManager() {
    /** Products in cart */
    val productsInCart by lazy { productsRepository.productsInCart }
    /** Sum for order */
    val totalInCart by lazy {
        Transformations.map(productsInCart) {
            it.map { it.priceSumInCart() }.sum()
        }
    }
    /** Message for order */
    fun message(total: String, rub: String): LiveData<String> {
        /** Text with products */
        val order = productsInCart.value?.joinToString(
            separator = ", ${System.lineSeparator()}",
            postfix = ".${System.lineSeparator()}"
        ) { it.textForOrder() }.orEmpty()
        /** Text with sum */
        val sum = "$total ${totalInCart.value} $rub"
        /** Text with products, sum & delivery info */
        return Transformations.map(profileRepository.profile) {
            "$order$sum${it?.delivery().orEmpty()}"
        }

    }

    fun clearCart() {
        viewModelScope.launch { productsRepository.clearCart() }
    }

    fun contacts() = contactsRepository.contacts()
}