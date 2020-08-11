package com.alesat1215.productsfromerokhin.load

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.*
import com.alesat1215.productsfromerokhin.util.Auth
import com.orhanobut.logger.Logger
import javax.inject.Inject

class LoadViewModel @Inject constructor(
    private val productsRepository: IProductsRepository,
    private val tutorialRepository: ITutorialRepository,
    private val contactsRepository: IContactsRepository,
    private val titlesRepository: ITitlesRepository,
    private val aboutProductsRepository: IAboutProductsRepository,
    private val aboutAppRepository: IAboutAppRepository,
    private val auth: Auth
) : ViewModel() {

    fun firebaseAuth() = auth.signIn()
    // Data loading triggers
    fun loadCompleteProducts() = Transformations.map(productsRepository.products()) { it.isNotEmpty() }
    fun loadCompleteTutorial() = Transformations.map(tutorialRepository.instructions()) { it.isNotEmpty() }
    fun loadCompletePhone() = Transformations.map(contactsRepository.contacts()) { it != null }
    fun loadCompleteTitles() = Transformations.map(titlesRepository.titles()) { it != null }
    fun loadCompleteAboutProducts() = Transformations.map(aboutProductsRepository.aboutProducts()) { it.isNotEmpty() }
    fun loadCompleteAboutApp() = Transformations.map(aboutAppRepository.aboutApp()) { it != null }

    fun loadDataComplete(): LiveData<Boolean> {
        val phone by lazy { Transformations.map(contactsRepository.contacts()) { it != null } }
        val titles by lazy { Transformations.map(titlesRepository.titles()) { it != null } }
        val products by lazy { Transformations.map(productsRepository.products()) { it.isNotEmpty() } }
        val aboutProducts by lazy { Transformations.map(aboutProductsRepository.aboutProducts()) { it.isNotEmpty() } }
        val aboutApp by lazy { Transformations.map(aboutAppRepository.aboutApp()) { it != null } }

        val phone_titles = Transformations.switchMap(phone) {
            if (it) {
                Logger.d("Load phone complete")
                return@switchMap titles
            } else {
                return@switchMap MutableLiveData(it)
            }
        }
        val phone_titles_products = Transformations.switchMap(phone_titles) {
            if (it) {
                Logger.d("Load titles complete")
                return@switchMap products
            } else {
                return@switchMap MutableLiveData(it)
            }
        }
        val phone_titles_products_aboutProducts = Transformations.switchMap(phone_titles_products) {
            if (it) {
                Logger.d("Load products complete")
                return@switchMap aboutProducts
            } else {
                return@switchMap MutableLiveData(it)
            }
        }

        return Transformations.switchMap(phone_titles_products_aboutProducts) {
            if (it) {
                Logger.d("Load about products complete")
                return@switchMap aboutApp
            } else {
                return@switchMap MutableLiveData(it)
            }
        }
    }
}