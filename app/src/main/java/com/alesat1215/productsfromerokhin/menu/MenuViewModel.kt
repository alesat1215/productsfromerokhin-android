package com.alesat1215.productsfromerokhin.menu

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {

    /** Scroll position for products_menu */
    var scrollPosition = 0

    fun products() = repository.products()

    fun groups() = repository.groups()

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            repository.addProductToCart(product.productInCart())
            Log.d("Menu", "Add to cart: ${product.productDB?.name}")
        }
    }

    fun delProductFromCart(product: Product) {
        viewModelScope.launch {
            if (product.inCart.isNotEmpty()) {
                val product2 = product.inCart.first()
                repository.delProductFromCart(product2)
                Log.d("Menu", "Del from cart: ${product2.name}")
            }
        }
    }
}