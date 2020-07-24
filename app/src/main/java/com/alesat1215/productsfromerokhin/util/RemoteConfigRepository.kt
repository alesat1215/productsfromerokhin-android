package com.alesat1215.productsfromerokhin.util

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

interface RemoteConfigRepository {
    val remoteConfig: FirebaseRemoteConfig

    fun fetchAndActivate(onSuccess: () -> Unit) {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Tutorial", "Remote config fetched: ${it.result}")
                onSuccess()
            } else Log.d("Tutorial", "Fetch remote config failed")
        }
    }
}