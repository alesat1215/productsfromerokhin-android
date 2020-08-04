package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AboutProductsRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) {
    private val aboutProducts by lazy { db.aboutProductsDao().aboutProducts() }

    fun aboutProducts(): LiveData<List<AboutProducts>> {

    }

    private suspend fun updateAboutProducts() = withContext(Dispatchers.Default) {
        val aboutProducts = gson.fromJson(
            dbUpdater.firebaseRemoteConfig.getString(ABOUT_PRODUCTS_LIST),
            Array<AboutProducts>::class.java
        ).asList()
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val ABOUT_PRODUCTS_LIST = "about_products_list"
        const val ABOUT_PRODUCTS_TITLE = "about_products_list_title"
    }
}