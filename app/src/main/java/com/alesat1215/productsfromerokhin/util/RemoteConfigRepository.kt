package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.orhanobut.logger.Logger
import javax.inject.Inject

/** Fetch Firebase remote config */
class RemoteConfigRepository @Inject constructor(
    val remoteConfig: FirebaseRemoteConfig,
    private val firebaseOnCompleteLiveData: FirebaseOnCompleteLiveData<Boolean>
) {
    fun fetchAndActivate(): LiveData<Result<Boolean>> {
        remoteConfig.fetchAndActivate().addOnCompleteListener(firebaseOnCompleteLiveData)
        return firebaseOnCompleteLiveData.result()
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

class FirebaseOnCompleteLiveData<TResut> @Inject constructor() : OnCompleteListener<TResut> {
    private val result = MutableLiveData<Result<TResut>>()

    override fun onComplete(p0: Task<TResut>) {
        if (p0.isSuccessful) {
            Logger.d("Firebase request is SUCCESS")
            result.value = Result.success(p0.result)
        } else {
            val exception = p0.exception ?: Exception(FirebaseAuthComplete.UNKNOWN_EXCEPTION)
            Logger.d("Firebase request is FAILED: $exception")
            result.value = Result.failure(exception)
        }
    }

    fun result(): LiveData<Result<TResut>> = result

    companion object {
        const val UNKNOWN_EXCEPTION = "Firebase unknown exception"
    }
}