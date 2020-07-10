package com.alesat1215.productsfromerokhin.menu

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {

    /** Scroll position for products_menu */
    var scrollPosition = 0

    fun products() = repository.products()

    fun groups() = repository.groups()
}