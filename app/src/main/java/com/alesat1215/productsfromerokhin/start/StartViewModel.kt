package com.alesat1215.productsfromerokhin.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.ProductInfo
import com.alesat1215.productsfromerokhin.start.StartTitle.*
import com.alesat1215.productsfromerokhin.util.CartManager
import javax.inject.Inject

class StartViewModel @Inject constructor(
    override val productsRepository: IProductsRepository
) : CartManager() {

    /** Save scroll position for lists */
    val scrollPosition = mutableMapOf<Int, Int>()

    /** @return title for type */
    fun title(forType: StartTitle) =
        when (forType) {
            TITLE -> Transformations.map(productsRepository.titles()) { it?.title }
            IMG -> Transformations.map(productsRepository.titles()) { it?.img }
            IMGTITLE -> Transformations.map(productsRepository.titles()) { it?.imgTitle }
            PRODUCTS -> Transformations.map(productsRepository.titles()) { it?.productsTitle }
            PRODUCTS2 -> Transformations.map(productsRepository.titles()) { it?.productsTitle2 }
        }

    /** @return products filtering by predicate */
    fun products(predicate: ((ProductInfo) -> Boolean)? = null): LiveData<List<ProductInfo>> {
        return if (predicate != null)
            Transformations.map(productsRepository.products()) { it.filter(predicate) }
        else productsRepository.products()
    }

}

/** Type of data in StartFragment */
enum class StartTitle {
    TITLE, IMGTITLE, IMG, PRODUCTS, PRODUCTS2
}