package com.alesat1215.productsfromerokhin.start

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.StartTitle

class StartViewModel: ViewModel() {
    private var repository = ProductsRepository()
    private var products = repository.products()

    fun title(forType: StartTitle) = repository.title(forType)

    fun products(predicate: ((Product) -> Boolean)? = null): LiveData<List<Product>> {
        return if (predicate != null)
            Transformations.map(products) { it.filter(predicate) }
        else products
    }
}