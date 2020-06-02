package com.alesat1215.productsfromerokhin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alesat1215.productsfromerokhin.data.Products
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        firebaseTest()
    }

    private fun firebaseTest() {
        auth.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("firebase", auth.currentUser.toString())
                db.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("firebase", error.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val products = snapshot.getValue(Products::class.java)
//                        Log.d("firebase", "${products?.groups?.size}")
//                        for (group in products?.groups ?: emptyList()) {
//                            Log.d("firebase", "${group.products?.size} ${group.order}")
//                        }
                        for (product in products?.productsWithGroupOrder() ?: emptyList()) {
                            Log.d("firebase", "${product.groupOrder} ${product.order}")
                        }
                    }

                })
//                db.collection("groups").addSnapshotListener { snapshot, exception ->
//                    if (exception != null) {
//                        Log.d("firebase", exception.localizedMessage)
//                        return@addSnapshotListener
//                    }
//                    if (snapshot != null) {
//                        Log.d("firebase", "${snapshot.size()}")
//                        val groups = snapshot.map { it.toObject(Group::class.java).apply { id = it.id } }
//                        for (group in groups) {
//                            Log.d("firebase", "${group.id} ${group.index} ${group.name}")
//                        }
//                        val products = groups.map {
//                            db.collection("groups").document(it.id).collection("products")
//                        }
//                    }
//                }
            } else Log.d("firebase", "Login failed")
        }
    }
}
