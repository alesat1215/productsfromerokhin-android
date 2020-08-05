package com.alesat1215.productsfromerokhin.aboutproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.AboutProducts
import com.alesat1215.productsfromerokhin.data.IAboutProductsRepository
import javax.inject.Inject

class AboutProductsViewModel @Inject constructor(
    private val aboutProductsRepository: IAboutProductsRepository
): ViewModel() {
    /** @return about products filtering by predicate */
    fun aboutProducts(predicate: ((AboutProducts) -> Boolean)? = null): LiveData<List<AboutProducts>> {
        return if (predicate != null)
            Transformations.map(aboutProductsRepository.aboutProducts()) { it.filter(predicate) }
        else aboutProductsRepository.aboutProducts()
    }
}