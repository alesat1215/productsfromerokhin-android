package com.alesat1215.productsfromerokhin.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.StartTitle
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    val groups = repository.groups()

    fun title(forType: StartTitle) =
        when (forType) {
            StartTitle.TITLE -> Transformations.map(repository.titles()) { it?.title }
            StartTitle.IMAGE -> Transformations.map(repository.titles()) { it?.imageTitle }
            StartTitle.LIST -> Transformations.map(repository.titles()) { it?.listTitle }
            StartTitle.LIST2 -> Transformations.map(repository.titles()) { it?.listTitle2 }
        }

    fun products(predicate: ((Product) -> Boolean)? = null): LiveData<List<Product>> {
        return if (predicate != null)
            Transformations.map(repository.products()) { it.filter(predicate) }
        else repository.products()
    }

}