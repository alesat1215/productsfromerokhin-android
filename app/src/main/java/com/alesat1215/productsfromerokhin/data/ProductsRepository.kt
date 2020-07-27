package com.alesat1215.productsfromerokhin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.withTransaction
import com.alesat1215.productsfromerokhin.data.local.*
import com.alesat1215.productsfromerokhin.di.AppModule.DBfb
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfigRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

interface IProductsRepository : RemoteConfigRepository {
    /** Get products & update Room from remote database if needed */
    fun products(): LiveData<List<Product>>
    /** Get titles & update Room from remote database if needed */
    fun titles(): LiveData<Titles>
    /** Get groups & update Room from remote database if needed */
    fun groups(): LiveData<List<GroupDB>>
    /** Get products in cart */
    val productsInCart: LiveData<List<Product>>
    /** Add product to cart */
    suspend fun addProductToCart(product: ProductInCart)
    /** Del product from cart */
    suspend fun delProductFromCart(product: ProductInCart)
    /** Del products from cart */
    suspend fun clearCart()
    /** Get delivery info */
    val profile: LiveData<Profile>
    /** Update profile data in db */
    suspend fun updateProfile(profile: Profile)
}

/** Repository for products, groups & titles.
 * Return LiveData from Room & update if needed Room from remote database.
 * */
@Singleton
class ProductsRepository @Inject constructor(
    /** Firebase remote config */
    override val remoteConfig: FirebaseRemoteConfig,
    /** Room database */
    private val db: ProductsDatabase,
    /** Limiting the frequency of queries to remote database */
    override val limiter: RateLimiter,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IProductsRepository {
    /** @return LiveData with products from Room only once */
    private val products by lazy { db.productsDao().products() }
    /** @return LiveData with groups from Room only once */
    private val groups by lazy { db.productsDao().groups() }
    /** @return LiveData with titles from Room only once */
    private val titles by lazy { db.titlesDao().titles() }
    /** @return LiveData with products in cart from Room only once */
    override val productsInCart by lazy { Transformations.map(products) { it.filter { it.inCart.isNotEmpty() } } }
    /** @return LiveData with delivery info from Room only once */
    override val profile by lazy { db.profileDao().profile() }

    override suspend fun updateProfile(profile: Profile) = withContext(Dispatchers.IO) {
        db.profileDao().updateProfile(profile)
    }

    /** Get products & update Room from remote database if needed */
    override fun products(): LiveData<List<Product>> {
        updateDB()
        return products
    }

    /** Get titles & update Room from remote database if needed */
    override fun titles(): LiveData<Titles> {
        updateDB()
        return titles
    }

    /** Get groups & update Room from remote database if needed */
    override fun groups(): LiveData<List<GroupDB>> {
        updateDB()
        return groups
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

    private fun updateDB() {
        /** Return if limit is over */
        if(!limiter.shouldFetch()) return
        fetchAndActivate {
            // Update data in Room
            GlobalScope.launch(Dispatchers.IO) {
                // Update products
                val groups = gson.fromJson(
                    remoteConfig.getString(RemoteConfigRepository.PRODUCTS),
                    Array<GroupDB>::class.java
                ).asList()
                db.productsDao().updateProducts(groups, products(groups))
                // Update titles
                val titles = gson.fromJson(
                    remoteConfig.getString(RemoteConfigRepository.TITLES),
                    Titles::class.java
                )
                db.titlesDao().updateTitles(titles)
            }
        }
    }

    /** Update Room from remote database if needed */
//    private fun updateDB() {
//        /** Return if limit is over */
//        if(!dbFBFetchLimit.shouldFetch()) return
//        /** Sign in to firebase */
//        signInFB {
//            /** Fetch data from remote database */
//            fetchFB {
//                /** Update data in Room */
//                if (it != null) {
//                    GlobalScope.launch(Dispatchers.IO) {
//                        db.withTransaction {
//                            db.productsDao().clearBeforeUpdate()
//                            db.productsDao().update(it)
//                        }
//                    }
//                } else Log.d("Firebase", "Remote data is null. db is NOT updated")
//            }
//        }
//    }

//    /** Sign in as anonymous to firebase & execute onSuccess function */
//    private fun signInFB(onSuccess: () -> Unit) {
//        /** If already sign in execute onSuccess */
//        if (authFB.currentUser != null) {
//            Log.d("Firebase", "Already sign in: ${authFB.currentUser}")
//            onSuccess()
//            return
//        }
//        /** Sign in as anonymous & execute onSuccess.
//         * Reset limiter if sign in failed
//         * */
//        authFB.signInAnonymously().addOnCompleteListener {
//            if (it.isSuccessful) {
//                Log.d("Firebase", "Sign in SUCCESS: ${authFB.currentUser}")
//                onSuccess()
//            } else {
//                dbFBFetchLimit.reset()
//                Log.d("Firebase", "Sign in FAILED: ${it.exception}")
//            }
//        }
//    }
//
//    /** Fetch data from remote database & execute onSuccess function */
//    private fun fetchFB(onSuccess: (IRemoteData?) -> Unit) {
//        /** Connect to remote database */
//        dbFB.database.goOnline()
//        dbFB.addListenerForSingleValueEvent(object : ValueEventListener {
//            /** Disconnect from remote database & reset limiter on error */
//            override fun onCancelled(error: DatabaseError) {
//                dbFBFetchLimit.reset()
//                dbFB.database.goOffline()
//                Log.d("Firebase", error.message)
//            }
//
//            /** Execute onSuccess function & disconnect from remote database */
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val data = snapshot.getValue(RemoteData::class.java)
//                Log.d("Firebase", "Get remote data with " +
//                        "title: ${data?.title}, " +
//                        "imageTitle: ${data?.imgTitle}, " +
//                        "productsTitle: ${data?.productsTitle}, " +
//                        "productsTitle2: ${data?.productsTitle2}, " +
//                        "group: ${data?.groups?.count()}, " +
//                        "products: ${data?.products()?.count()}"
//                )
//                onSuccess(data)
//                dbFB.database.goOffline()
//            }
//
//        })
//    }

}