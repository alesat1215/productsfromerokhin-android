package com.alesat1215.productsfromerokhin.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.StartTitle

class StartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProductsRepository(application)
    private val products = repository.products_
    val groups = repository.groups

    fun title(forType: StartTitle) = repository.title(forType)

    fun products(predicate: ((Product) -> Boolean)? = null): LiveData<List<Product>> {
        return if (predicate != null)
            Transformations.map(products) { it.filter(predicate) }
        else products
    }
}