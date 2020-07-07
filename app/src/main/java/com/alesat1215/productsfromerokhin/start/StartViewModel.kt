package com.alesat1215.productsfromerokhin.start

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.start.StartTitle.*
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {

    /** @return title for type */
    fun title(forType: StartTitle) =
        when (forType) {
            TITLE -> Transformations.map(repository.titles()) { it?.title }
            IMG -> Transformations.map(repository.titles()) { it?.img }
            IMGTITLE -> Transformations.map(repository.titles()) { it?.imgTitle }
            PRODUCTS -> Transformations.map(repository.titles()) { it?.productsTitle }
            PRODUCTS2 -> Transformations.map(repository.titles()) { it?.productsTitle2 }
        }

    /** @return products filtering by predicate */
    fun products(predicate: ((Product) -> Boolean)? = null): LiveData<List<Product>> {
        return if (predicate != null)
            Transformations.map(repository.products()) { it.filter(predicate) }
        else repository.products()
    }

    val scrollPosition = mutableMapOf<String, Parcelable>()

}

/** Type of data in StartFragment */
enum class StartTitle {
    TITLE, IMGTITLE, IMG, PRODUCTS, PRODUCTS2
}