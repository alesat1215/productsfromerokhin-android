package com.alesat1215.productsfromerokhin.aboutproducts

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.AboutProducts
import com.alesat1215.productsfromerokhin.data.IAboutProductsRepository
import javax.inject.Inject

class AboutProductsViewModel @Inject constructor(
    private val aboutProductsRepository: IAboutProductsRepository
) : ViewModel() {
    /** @return about products filtering by predicate */
    fun aboutProducts(predicate: ((AboutProducts) -> Boolean)) =
        Transformations.map(aboutProductsRepository.aboutProducts()) { it.filter(predicate) }
}