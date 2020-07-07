package com.alesat1215.productsfromerokhin.menu

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val repository: IProductsRepository
) : ViewModel() {

    /** Save state for list to remember scroll position */
    val recyclerViewState = mutableMapOf<Int, Parcelable>()

    var selectedTabPosition = 0

    fun products() = repository.products()

    fun groups() = repository.groups()
}