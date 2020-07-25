package com.alesat1215.productsfromerokhin.load

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.ITutorialRepository
import javax.inject.Inject

class LoadViewModel @Inject constructor(
    private val productsRepository: IProductsRepository,
    private val tutorialRepository: ITutorialRepository
) : ViewModel() {

    /** Trigger of data loading */
    fun loadCompleteProducts() = Transformations.map(productsRepository.products()) { it.isNotEmpty() }
    fun loadCompleteTutorial() = Transformations.map(tutorialRepository.instructions()) { it.isNotEmpty() }
}