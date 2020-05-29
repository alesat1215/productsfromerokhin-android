package com.alesat1215.productsfromerokhin.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.ProductsRepository

class StartViewModel: ViewModel() {
    private var repository = ProductsRepository()
    private var products = repository.products()

    fun products(predicate: ((Product) -> Boolean)? = null): LiveData<List<Product>> {
        return if (predicate != null)
            Transformations.map(products) { it.filter(predicate) }
        else products
    }
}