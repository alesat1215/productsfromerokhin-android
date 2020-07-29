package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.orhanobut.logger.Logger
import javax.inject.Inject

/** OnCompleteListener for Firebase with LiveData result */
class FirebaseOnComplete<TResut> @Inject constructor() : OnCompleteListener<TResut> {
    /** Result of OnComplete */
    private val result = MutableLiveData<Result<Unit>>()

    override fun onComplete(p0: Task<TResut>) {
        // Success
        if (p0.isSuccessful) {
            Logger.d("Firebase request is SUCCESS")
            result.value = Result.success(Unit)
        // Failed
        } else {
            val exception = p0.exception ?: Exception(UNKNOWN_EXCEPTION)
            Logger.d("Firebase request is FAILED: $exception")
            result.value = Result.failure(exception)
        }
    }
    /** @return result of OnComplete */
    fun result(): LiveData<Result<Unit>> = result

    companion object {
        /** Used as default value for exception from firebase */
        const val UNKNOWN_EXCEPTION = "Firebase unknown exception"
    }
}