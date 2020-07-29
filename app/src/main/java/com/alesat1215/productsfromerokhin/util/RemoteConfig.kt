package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject

/** Fetch Firebase remote config */
class RemoteConfig @Inject constructor(
    val firebaseRemoteConfig: FirebaseRemoteConfig,
    private val firebaseOnComplete: FirebaseOnComplete<Boolean>
) {
    fun fetchAndActivate(): LiveData<Result<Unit>> {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(firebaseOnComplete)
        return firebaseOnComplete.result()
    }

//    val limiter: RateLimiter?
//    /** Fetch & activate remote config & exec onSuccess() */
//    fun fetchAndActivate(onSuccess: () -> Unit) {
//        remoteConfig.fetchAndActivate().addOnCompleteListener {
//            if (it.isSuccessful) {
//                Logger.d("Remote config fetched: ${it.result}")
//                onSuccess()
//            } else {
//                Logger.d("Fetch remote config failed")
//                limiter?.reset()
//            }
//        }
//    }

    /** Parameters in Firebase remote config */
    companion object {
        const val TITLES = "titles"
        const val PRODUCTS = "products"
        const val INSTRUCTIONS = "instructions"
    }
}