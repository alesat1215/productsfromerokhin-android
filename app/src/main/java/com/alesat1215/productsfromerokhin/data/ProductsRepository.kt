package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProductsRepository {
    private val products = Array(10) { Product("Name name", price = 250) }.asList()

    fun products(): LiveData<List<Product>> = MutableLiveData(products)
}