package com.alesat1215.productsfromerokhin.load

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IPhoneRepository
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.ITitlesRepository
import com.alesat1215.productsfromerokhin.data.ITutorialRepository
import com.alesat1215.productsfromerokhin.util.Auth
import javax.inject.Inject

class LoadViewModel @Inject constructor(
    private val productsRepository: IProductsRepository,
    private val tutorialRepository: ITutorialRepository,
    private val phoneRepository: IPhoneRepository,
    private val titlesRepository: ITitlesRepository,
    private val auth: Auth
) : ViewModel() {

    fun firebaseAuth() = auth.signIn()
    // Data loading triggers
    fun loadCompleteProducts() = Transformations.map(productsRepository.products()) { it.isNotEmpty() }
    fun loadCompleteTutorial() = Transformations.map(tutorialRepository.instructions()) { it.isNotEmpty() }
    fun loadCompletePhone() = Transformations.map(phoneRepository.phone()) { it?.phone?.isNotEmpty() ?: false }
    fun loadCompleteTitles() = Transformations.map(titlesRepository.titles()) { it != null }
}