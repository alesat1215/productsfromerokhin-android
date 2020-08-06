package com.alesat1215.productsfromerokhin.load

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.*
import com.alesat1215.productsfromerokhin.util.Auth
import javax.inject.Inject

class LoadViewModel @Inject constructor(
    private val productsRepository: IProductsRepository,
    private val tutorialRepository: ITutorialRepository,
    private val contactsRepository: IContactsRepository,
    private val titlesRepository: ITitlesRepository,
    private val aboutProductsRepository: IAboutProductsRepository,
    private val auth: Auth
) : ViewModel() {

    fun firebaseAuth() = auth.signIn()
    // Data loading triggers
    fun loadCompleteProducts() = Transformations.map(productsRepository.products()) { it.isNotEmpty() }
    fun loadCompleteTutorial() = Transformations.map(tutorialRepository.instructions()) { it.isNotEmpty() }
    fun loadCompletePhone() = Transformations.map(contactsRepository.contacts()) { it != null }
    fun loadCompleteTitles() = Transformations.map(titlesRepository.titles()) { it != null }
    fun loadCompleteAboutProducts() = Transformations.map(aboutProductsRepository.aboutProducts()) { it.isNotEmpty() }
}