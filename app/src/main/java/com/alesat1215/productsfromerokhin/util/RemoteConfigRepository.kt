package com.alesat1215.productsfromerokhin.util

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.orhanobut.logger.Logger

/** Fetch Firebase remote config */
interface RemoteConfigRepository {
    val remoteConfig: FirebaseRemoteConfig
    val limiter: RateLimiter?
    /** Fetch & activate remote config & exec onSuccess() */
    fun fetchAndActivate(onSuccess: () -> Unit) {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                Logger.d("Remote config fetched: ${it.result}")
                onSuccess()
            } else {
                Logger.d("Fetch remote config failed")
                limiter?.reset()
            }
        }
    }
    /** Parameters in Firebase remote config */
    companion object {
        const val TITLES = "titles"
        const val PRODUCTS = "products"
        const val INSTRUCTIONS = "instructions"
    }
}