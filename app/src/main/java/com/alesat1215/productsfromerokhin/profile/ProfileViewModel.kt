package com.alesat1215.productsfromerokhin.profile

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    repository: IProductsRepository
): ViewModel() {
    val profile = repository.profile
}