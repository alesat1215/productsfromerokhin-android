package com.alesat1215.productsfromerokhin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.withTransaction
import com.alesat1215.productsfromerokhin.di.AppModule.DBfb
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

/** Repository for products, groups & titles.
 * Return LiveData from Room & update if needed Room from remote database.
 * */
@Singleton
class ProductsRepository @Inject constructor(
    private val authFB: FirebaseAuth,
    /** Remote database */
    private val dbFB: DatabaseReference,
    /** Room database */
    private val db: ProductsDatabase,
    /** Limiting the frequency of queries to a remote database */
    @DBfb private val dbFBFetchLimit: RateLimiter
) {
    /** @return LiveData with products from Room only once */
    private val products by lazy { db.productsDao().products() }
    /** @return LiveData with groups from Room only once */
    private val groups by lazy { db.productsDao().groups() }
    /** @return LiveData with titles from Room only once */
    private val titles by lazy { db.productsDao().titles() }

    /** Get products & update Room from remote database if needed */
    fun products(): LiveData<List<Product>> {
        updateDB()
        return products
    }

    /** Get titles & update Room from remote database if needed */
    fun titles(): LiveData<RemoteData?> {
        updateDB()
        return titles
    }

    /** Get groups & update Room from remote database if needed */
    fun groups(): LiveData<List<Group>> {
        updateDB()
        return groups
    }

    /** Update Room from remote database if needed */
    private fun updateDB() {
        /** Return if limit is over */
        if(!dbFBFetchLimit.shouldFetch()) return
        /** Sign in to firebase */
        signInFB {
            /** Fetch data from remote database */
            fetchFB {
                /** Update data in Room */
                if (it != null) {
                    GlobalScope.launch(Dispatchers.IO) {
                        db.withTransaction {
                            db.clearAllTables()
                            db.productsDao().update(it)
                            Log.d("Firebase", "db is updated")
                        }
                    }
                } else Log.d("Firebase", "Remote data is null. db is NOT updated")
            }
        }
    }

    /** Sign in as anonymous to firebase & execute onSuccess function */
    private fun signInFB(onSuccess: () -> Unit) {
        /** If already sign in execute onSuccess */
        if (authFB.currentUser != null) {
            Log.d("Firebase", "Already sign in: ${authFB.currentUser}")
            onSuccess()
            return
        }
        /** Sign in as anonymous & execute onSuccess.
         * Reset limiter if sign in failed
         * */
        authFB.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Firebase", "Sign in SUCCESS: ${authFB.currentUser}")
                onSuccess()
            } else {
                dbFBFetchLimit.reset()
                Log.d("Firebase", "Sign in FAILED: ${it.exception}")
            }
        }
    }

    /** Fetch data from remote database & execute onSuccess function */
    private fun fetchFB(onSuccess: (RemoteData?) -> Unit) {
        /** Connect to remote database */
        dbFB.database.goOnline()
        dbFB.addListenerForSingleValueEvent(object : ValueEventListener {
            /** Disconnect from remote database & reset limiter on error */
            override fun onCancelled(error: DatabaseError) {
                dbFBFetchLimit.reset()
                dbFB.database.goOffline()
                Log.d("Firebase", error.message)
            }

            /** Execute onSuccess function & disconnect from remote database */
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(RemoteData::class.java)
                Log.d("Firebase", "Get remote data with " +
                        "title: ${data?.title}, " +
                        "imageTitle: ${data?.imgTitle}, " +
                        "productsTitle: ${data?.productsTitle}, " +
                        "productsTitle2: ${data?.productsTitle2}, " +
                        "group: ${data?.groups?.count()}, " +
                        "products: ${data?.productsWithGroupId()?.count()}"
                )
                onSuccess(data)
                dbFB.database.goOffline()
            }

        })
    }

}