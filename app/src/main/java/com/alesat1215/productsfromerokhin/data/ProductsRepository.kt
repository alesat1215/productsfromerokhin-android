package com.alesat1215.productsfromerokhin.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import androidx.room.withTransaction
import com.alesat1215.productsfromerokhin.data.StartTitle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*

class ProductsRepository(private val application: Application) {
    private val auth = FirebaseAuth.getInstance()
    private val dbFB = FirebaseDatabase.getInstance().reference

    private val db by lazy { Room.databaseBuilder(application, ProductsDatabase::class.java, "productsDatabase").build() }
    val products by lazy { db.productsDao().products() }
    val groups by lazy { db.productsDao().groups() }
    val titles by lazy { db.productsDao().titles() }

    init {
        updateDB()
    }

    private fun updateDB() {
        auth.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("firebase", auth.currentUser.toString())
                dbFB.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("firebase", error.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data = snapshot.getValue(RemoteData::class.java)
                        Log.d("firebase", "${data?.title} ${data?.imageTitle} ${data?.listTitle} ${data?.listTitle2}")
                        for (product in data?.productsWithGroupId() ?: emptyList()) {
                            Log.d("firebase", "${product.group} ${product.id}")
                        }
                        if (data != null)
                            GlobalScope.launch(Dispatchers.IO) {
                                db.withTransaction {
                                    db.clearAllTables()
                                    db.productsDao().update(data)
                                    Log.d("firebase", "db is updated")
                                }
                            }
                    }

                })
            } else Log.d("firebase", "Login failed")
        }
    }
}

enum class StartTitle {
    TITLE, IMAGE, LIST, LIST2
}