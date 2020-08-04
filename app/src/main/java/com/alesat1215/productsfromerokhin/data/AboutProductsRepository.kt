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
    /** Get about products title & update Room from remote config if needed */
    fun aboutProductsTitle(): LiveData<AboutProductsTitle?>
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
    /** @return LiveData with about products title from Room only once */
    private val aboutProductsTitle by lazy { db.aboutProductsDao().aboutProductsTitle() }

    override fun aboutProducts(): LiveData<List<AboutProducts>> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateAboutProducts)) { aboutProducts }
    }

    override fun aboutProductsTitle(): LiveData<AboutProductsTitle?> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateAboutProducts)) { aboutProductsTitle }
    }

    /** Get about product & title from remote config & update db in background */
    private suspend fun updateAboutProducts() = withContext(Dispatchers.Default) {
        // Get about_products_list
        val aboutProducts = gson.fromJson(
            dbUpdater.firebaseRemoteConfig.getString(ABOUT_PRODUCTS_LIST),
            Array<AboutProducts>::class.java
        ).asList()
        // Get about_products_title
        val aboutProductsTitle = dbUpdater.firebaseRemoteConfig.getString(ABOUT_PRODUCTS_TITLE)
        Logger.d("Fetch from remote config about products: ${aboutProducts.count()}, title: $aboutProductsTitle")
        // Update db
        db.aboutProductsDao().updateAboutProducts(aboutProducts, AboutProductsTitle(aboutProductsTitle))
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val ABOUT_PRODUCTS_LIST = "about_products_list"
        const val ABOUT_PRODUCTS_TITLE = "about_products_title"
    }
}