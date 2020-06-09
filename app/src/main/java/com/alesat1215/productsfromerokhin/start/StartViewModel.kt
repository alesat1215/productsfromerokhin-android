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

    /** @return title for type */
    fun title(forType: StartTitle) =
        when (forType) {
            StartTitle.TITLE -> Transformations.map(repository.titles()) { it?.title }
            StartTitle.IMG -> Transformations.map(repository.titles()) { it?.img }
            StartTitle.IMGTITLE -> Transformations.map(repository.titles()) { it?.imgTitle }
            StartTitle.PRODUCTS -> Transformations.map(repository.titles()) { it?.productsTitle }
            StartTitle.PRODUCTS2 -> Transformations.map(repository.titles()) { it?.productsTitle2 }
        }

    /** @return products filtering by predicate */
    fun products(predicate: ((Product) -> Boolean)? = null): LiveData<List<Product>> {
        return if (predicate != null)
            Transformations.map(repository.products()) { it.filter(predicate) }
        else repository.products()
    }

}