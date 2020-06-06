package com.alesat1215.productsfromerokhin.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.withTransaction
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class ProductsRepository(private val application: Application) {
    private val auth = FirebaseAuth.getInstance()
    private val dbFB = FirebaseDatabase.getInstance().reference

    private val dbFBFetchLimit = RateLimiter(2, TimeUnit.MINUTES)

    private val db by lazy { Room.databaseBuilder(application, ProductsDatabase::class.java, "productsDatabase").build() }
    private val products by lazy { db.productsDao().products() }
    private val groups by lazy { db.productsDao().groups() }
    private val titles by lazy { db.productsDao().titles() }

    fun products(): LiveData<List<Product>> {
        updateDB()
        return products
    }

    fun titles(): LiveData<RemoteData?> {
        updateDB()
        return titles
    }

    fun groups(): LiveData<List<Group>> {
        updateDB()
        return groups
    }

    private fun updateDB() {
        if(!dbFBFetchLimit.shouldFetch()) return
        signInFB {
            fetchFB {
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

    private fun signInFB(onSuccess: () -> Unit) {
        if (auth.currentUser != null) {
            Log.d("Firebase", "Already sign in: ${auth.currentUser}")
            onSuccess()
            return
        }
        auth.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Firebase", "Sign in SUCCESS: ${auth.currentUser}")
                onSuccess()
            } else {
                dbFBFetchLimit.reset()
                Log.d("Firebase", "Sign in FAILED: ${it.exception}")
            }
        }
    }

    private fun fetchFB(onSuccess: (RemoteData?) -> Unit) {
        dbFB.database.goOnline()
        dbFB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                dbFBFetchLimit.reset()
                dbFB.database.goOffline()
                Log.d("Firebase", error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(RemoteData::class.java)
                Log.d("Firebase", "${data?.title} ${data?.imageTitle} ${data?.listTitle} ${data?.listTitle2}")
                for (product in data?.productsWithGroupId() ?: emptyList()) {
                    Log.d("Firebase", "${product.group} ${product.id}")
                }
                onSuccess(data)
                dbFB.database.goOffline()
            }

        })
    }

}

enum class StartTitle {
    TITLE, IMAGE, LIST, LIST2
}