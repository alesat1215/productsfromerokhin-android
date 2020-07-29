package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject

/** Fetch & activate Firebase remote config */
class RemoteConfig @Inject constructor(
    val firebaseRemoteConfig: FirebaseRemoteConfig,
    private val firebaseOnComplete: FirebaseOnComplete<Boolean>
) {
    /** @return result of fetch & activate */
    fun fetchAndActivate(): LiveData<Result<Unit>> {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(firebaseOnComplete)
        return firebaseOnComplete.result()
    }
}