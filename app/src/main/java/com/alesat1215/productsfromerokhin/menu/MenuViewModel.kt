package com.alesat1215.productsfromerokhin.menu

import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.util.CartManager
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    override val repository: IProductsRepository
) : CartManager() {

    /** Scroll position for products_menu */
    var scrollPosition = 0

    fun products() = repository.products()

    fun groups() = repository.groups()
}