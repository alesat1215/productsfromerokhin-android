package com.alesat1215.productsfromerokhin.menu

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {

    fun products() = repository.products()

    fun groups() = repository.groups()
}