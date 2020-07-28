package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.orhanobut.logger.Logger
import javax.inject.Inject
import javax.inject.Singleton

/** Sign in to FirebaseAuth */
@Singleton
class Auth @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authComplete: FirebaseAuthComplete
) {
    /** @return result of sign in */
    fun signIn(): LiveData<Result<Unit>> {
        // Already sign in
        if (firebaseAuth.currentUser != null) {
            Logger.d("Already sign in: ${firebaseAuth.currentUser}")
            return MutableLiveData(Result.success(Unit))
        }
        // Sign in as anonymous
        firebaseAuth.signInAnonymously().addOnCompleteListener(authComplete)
        return authComplete.authResult()
    }
}
/** OnComplete callback for FirebaseAuth */
class FirebaseAuthComplete @Inject constructor() : OnCompleteListener<AuthResult> {
    /** Result of sign in */
    private val result = MutableLiveData<Result<Unit>>()

    override fun onComplete(p0: Task<AuthResult>) {
        // Set success to result
        if(p0.isSuccessful) {
            Logger.d("Sign in SUCCESS: ${p0.result.user}")
            result.value = Result.success(Unit)
        // Set failure to result
        } else {
            val exception = p0.exception ?: Exception(UNKNOWN_EXCEPTION)
            Logger.d("Sign in FAILED: $exception")
            result.value = Result.failure(Throwable(exception))
        }
    }
    /** @return LiveData with result of sign in */
    fun authResult(): LiveData<Result<Unit>> = result

    companion object {
        /** Exception for sign in failure & exception is null */
        const val UNKNOWN_EXCEPTION = "Firebase Auth unknown exception"
    }

}