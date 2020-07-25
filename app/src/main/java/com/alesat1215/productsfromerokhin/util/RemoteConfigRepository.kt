package com.alesat1215.productsfromerokhin.util

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

/** Fetch Firebase remote config */
interface RemoteConfigRepository {
    val remoteConfig: FirebaseRemoteConfig
    val limiter: RateLimiter?
    /** Fetch & activate remote config & exec onSuccess() */
    fun fetchAndActivate(onSuccess: () -> Unit) {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Remote config", "Remote config fetched: ${it.result}")
                onSuccess()
            } else {
                Log.d("Remote config", "Fetch remote config failed")
                limiter?.reset()
            }
        }
    }
}