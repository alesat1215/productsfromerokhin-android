package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
//import androidx.lifecycle.liveData
//import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
//import com.alesat1215.productsfromerokhin.util.IDatabaseUpdater
//import com.alesat1215.productsfromerokhin.util.UpdateLimiter
//import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

/** Repository for products & groups.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
interface IProductsRepository {
    /** Get products & update Room from remote config if needed */
    fun products(): LiveData<List<ProductInfo>>
    /** Get groups & update Room from remote config if needed */
    fun groups(): LiveData<List<Group>>
    /** Get products in cart */
    val productsInCart: LiveData<List<ProductInfo>>
    /** Add product to cart */
    suspend fun addProductToCart(product: ProductInCart)
    /** Del product from cart */
    suspend fun delProductFromCart(product: ProductInCart)
    /** Del products from cart */
    suspend fun clearCart()
}

@Singleton
class ProductsRepository @Inject constructor(
//    /** Firebase remote config */
//    override val remoteConfig: RemoteConfig,
    /** Room database */
    private val db: AppDatabase,
//    /** Limiting the frequency of queries to remote config & update db */
//    override val limiter: UpdateLimiter,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IProductsRepository {
    /** @return LiveData with products from Room only once */
    private val products by lazy { db.productsDao().products() }
    /** @return LiveData with groups from Room only once */
    private val groups by lazy { db.productsDao().groups() }
    /** @return LiveData with products in cart from Room only once */
    override val productsInCart by lazy { Transformations.map(products) { it.filter { it.inCart.isNotEmpty() } } }

    /** Get products & update Room from remote config if needed */
    override fun products(): LiveData<List<ProductInfo>> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateProducts)) { products }
    }

    /** Get groups & update Room from remote config if needed */
    override fun groups(): LiveData<List<Group>> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateProducts)) { groups }
    }

    override suspend fun addProductToCart(product: ProductInCart) = withContext(Dispatchers.IO) {
        db.cartDao().insertProductInCart(product)
    }

    override suspend fun delProductFromCart(product: ProductInCart) = withContext(Dispatchers.IO) {
        db.cartDao().deleteProductFromCart(product)
    }

    override suspend fun clearCart() = withContext(Dispatchers.IO) {
        db.cartDao().clearCart()
    }

//    private fun updateDB(): LiveData<Unit> {
//        return Transformations.switchMap(databaseUpdater.needUpdate()) {
//            liveData {
//                it.onSuccess { emit(updateProducts()) }
//                it.onFailure { emit(Unit) }
//            }
//        }
//    }
//    /** Update Room from remote config if needed */
//    override fun updateDB(): LiveData<Result<Unit>> {
//        /** Return if limit is over */
//        if(limiter.needUpdate().not()) return MutableLiveData(Result.success(Unit))
//        // Fetch data from remote config & update db
//        return Transformations.switchMap(remoteConfig.fetchAndActivate()) {
//            liveData {
//                it.onSuccess { emit(Result.success(updateProducts())) }
//                it.onFailure {
//                    Logger.d("Fetch remote config FAILED: ${it.localizedMessage}")
//                    emit(Result.failure(it))
//                }
//            }
//        }
//    }
    /** Get groups, products & titles from remote config & update db in background */
    private suspend fun updateProducts() = withContext(Dispatchers.Default) {
        // Get groups with products from JSON
        val groups = gson.fromJson(
            dbUpdater.firebaseRemoteConfig.getString(PRODUCTS),
            Array<Group>::class.java
        ).asList()
        // Get products from groups
        val products =
            products(groups)
        Logger.d("Fetch from remote config groups: ${groups.count()}, products: ${products.count()}")
        // Update products
        db.productsDao().updateProducts(groups, products)
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val PRODUCTS = "products"
    }

}