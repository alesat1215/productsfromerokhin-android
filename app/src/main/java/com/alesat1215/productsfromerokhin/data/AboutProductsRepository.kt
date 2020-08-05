package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/** Repository for about products.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
interface IAboutProductsRepository {
    /** Get about products & update Room from remote config if needed */
    fun aboutProducts(): LiveData<List<AboutProducts>>
}
/**
 *
 * */
@Singleton
class AboutProductsRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IAboutProductsRepository {
    /** @return LiveData with about products from Room only once */
    private val aboutProducts by lazy { db.aboutProductsDao().aboutProducts() }

    override fun aboutProducts(): LiveData<List<AboutProducts>> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateAboutProducts)) { aboutProducts }
    }

    /** Get about product & title from remote config & update db in background */
    private suspend fun updateAboutProducts() = withContext(Dispatchers.Default) {
        // Get about_products_list
        val aboutProducts = gson.fromJson(
            dbUpdater.firebaseRemoteConfig.getString(ABOUT_PRODUCTS_LIST),
            Array<AboutProducts>::class.java
        ).asList()
        // Update db
        db.aboutProductsDao().updateAboutProducts(aboutProducts)
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val ABOUT_PRODUCTS_LIST = "about_products_list"
    }
}