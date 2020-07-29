package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.data.local.*
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

interface IProductsRepository {
    /** Get products & update Room from remote config if needed */
    fun products(): LiveData<List<ProductInfo>>
    /** Get titles & update Room from remote config if needed */
    fun titles(): LiveData<Titles>
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

/** Repository for products, groups & titles.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
@Singleton
class ProductsRepository @Inject constructor(
    /** Firebase remote config */
    private val remoteConfig: RemoteConfig,
    /** Room database */
    private val db: AppDatabase,
    /** Limiting the frequency of queries to remote database */
    private val limiter: RateLimiter,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IProductsRepository {
    /** Parameters in Firebase remote config */
    private val TITLES = "titles"
    private val PRODUCTS = "products"
    /** @return LiveData with products from Room only once */
    private val products by lazy { db.productsDao().products() }
    /** @return LiveData with groups from Room only once */
    private val groups by lazy { db.productsDao().groups() }
    /** @return LiveData with titles from Room only once */
    private val titles by lazy { db.titlesDao().titles() }
    /** @return LiveData with products in cart from Room only once */
    override val productsInCart by lazy { Transformations.map(products) { it.filter { it.inCart.isNotEmpty() } } }

    /** Get products & update Room from remote config if needed */
    override fun products(): LiveData<List<ProductInfo>> {
        return Transformations.switchMap(updateDB()) { products }
    }

    /** Get titles & update Room from remote config if needed */
    override fun titles(): LiveData<Titles> {
        return Transformations.switchMap(updateDB()) { titles }
    }

    /** Get groups & update Room from remote config if needed */
    override fun groups(): LiveData<List<Group>> {
        return Transformations.switchMap(updateDB()) { groups }
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
    /** Update Room from remote config if needed */
    private fun updateDB(): LiveData<Result<Unit>> {
        /** Return if limit is over */
        if(!limiter.shouldFetch()) return MutableLiveData(Result.success(Unit))
        // Fetch data from remote config & update db
        return Transformations.map(remoteConfig.fetchAndActivate()) {
            it.onSuccess { updateProducts() }
            it.onFailure { Logger.d("Fetch remote config FAILED: ${it.localizedMessage}") }
            it
        }
    }
    /** Get groups, products & titles from remote config & update db in background */
    private fun updateProducts() {
        // Update data in Room
        GlobalScope.launch(Dispatchers.IO) {
            // Get groups with products from JSON
            val groups = gson.fromJson(
                remoteConfig.firebaseRemoteConfig.getString(PRODUCTS),
                Array<Group>::class.java
            ).asList()
            // Get products from groups
            val products = products(groups)
            Logger.d("Fetch from remote config groups: ${groups.count()}, products: ${products.count()}")
            // Update products
            db.productsDao().updateProducts(groups, products)

            // Get titles from JSON
            val titles = gson.fromJson(
                remoteConfig.firebaseRemoteConfig.getString(TITLES),
                Titles::class.java
            )
            Logger.d("Fetch from remote config titles: " +
                    "${titles.title}, ${titles.imgTitle}, ${titles.productsTitle}, ${titles.productsTitle2}, ${titles.img}")
            // Update titles
            db.titlesDao().updateTitles(titles)
        }
    }

}