package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.orhanobut.logger.Logger
import javax.inject.Inject

class FirebaseOnComplete<TResut> @Inject constructor() : OnCompleteListener<TResut> {
    private val result = MutableLiveData<Result<Unit>>()

    override fun onComplete(p0: Task<TResut>) {
        if (p0.isSuccessful) {
            Logger.d("Firebase request is SUCCESS")
            result.value = Result.success(Unit)
        } else {
            val exception = p0.exception ?: Exception(UNKNOWN_EXCEPTION)
            Logger.d("Firebase request is FAILED: $exception")
            result.value = Result.failure(exception)
        }
    }

    fun result(): LiveData<Result<Unit>> = result

    companion object {
        const val UNKNOWN_EXCEPTION = "Firebase unknown exception"
    }
}