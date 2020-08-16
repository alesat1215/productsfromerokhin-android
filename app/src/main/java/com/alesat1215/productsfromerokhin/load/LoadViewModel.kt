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
    private val orderWarningRepository: IOrderWarningRepository,
    private val auth: Auth
) : ViewModel() {

    fun firebaseAuth() = auth.signIn()

    fun loadTutorialComplete() = Transformations.map(tutorialRepository.instructions()) { it.isNotEmpty() }
    // Data loading triggers
    fun loadDataComplete(): LiveData<Boolean> {
        val phone = Transformations.map(contactsRepository.contacts()) { it != null }
        val titles = Transformations.map(titlesRepository.titles()) { it != null }
        val products = Transformations.map(productsRepository.products()) { it.isNotEmpty() }
        val aboutProducts = Transformations.map(aboutProductsRepository.aboutProducts()) { it.isNotEmpty() }
        val aboutApp = Transformations.map(aboutAppRepository.aboutApp()) { it != null }
        val orderWarning = Transformations.map(orderWarningRepository.orderWarning()) { it != null }

        return listOf(phone, titles, products, aboutProducts, aboutApp, orderWarning).reduce { acc, liveData ->
            Transformations.switchMap(acc) {
                if (it) {
                    Logger.d("Load complete")
                    return@switchMap liveData
                } else {
                    Logger.d("Load incomplete")
                    return@switchMap MutableLiveData(it)
                }
            }
        }

    }
}