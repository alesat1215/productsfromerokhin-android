package com.alesat1215.productsfromerokhin.load

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import javax.inject.Inject

class LoadViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {

    /** Trigger of data loading */
    fun loadComplete() = Transformations.map(repository.products()) { it.isNotEmpty() }
}