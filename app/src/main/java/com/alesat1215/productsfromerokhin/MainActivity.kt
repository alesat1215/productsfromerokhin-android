package com.alesat1215.productsfromerokhin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alesat1215.productsfromerokhin.data.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

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
                db.collection("groups").addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Log.d("firebase", exception.localizedMessage)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        Log.d("firebase", "${snapshot.size()}")
                        val groups = snapshot.map { it.toObject(Group::class.java).apply { id = it.id } }
                        for (group in groups) {
                            Log.d("firebase", "${group.id} ${group.index} ${group.name}")
                        }
                    }
                }
            } else Log.d("firebase", "Login failed")
        }
    }
}
