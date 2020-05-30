package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.StartTitle.*

class ProductsRepository {
    private val products = Array(10) { Product("Name name", price = 250) }.asList()

    fun title(forType: StartTitle): LiveData<String> =
        when (forType) {
            TITLE -> MutableLiveData("Title")
            IMAGE -> MutableLiveData("Image title")
            LIST -> MutableLiveData("List title")
            LIST2 -> MutableLiveData("List title2")
        }

    fun products(): LiveData<List<Product>> = MutableLiveData(products)
}

enum class StartTitle {
    TITLE, IMAGE, LIST, LIST2
}