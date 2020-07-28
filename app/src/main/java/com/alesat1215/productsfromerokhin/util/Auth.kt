package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.orhanobut.logger.Logger
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Auth @Inject constructor(
    private val auth: FirebaseAuth,
    private val authComplete: AuthComplete
) {

    fun signIn(): LiveData<Result<Unit>> {
        if (auth.currentUser != null) {
            Logger.d("Already sign in: ${auth.currentUser}")
            return MutableLiveData(Result.success(Unit))
        }
        auth.signInAnonymously().addOnCompleteListener(authComplete)
        return authComplete.authResult()
    }
}

class AuthComplete @Inject constructor() : OnCompleteListener<AuthResult> {
    private val result = MutableLiveData<Result<Unit>>()

    override fun onComplete(p0: Task<AuthResult>) {
        if(p0.isSuccessful) {
            Logger.d("Sign in SUCCESS: ${p0.result.user}")
            result.value = Result.success(Unit)
        } else {
            Logger.d("Sign in FAILED: ${p0.exception}")
            result.value = Result.failure(throw p0.exception ?: UNKNOWN_EXCEPTION)
        }
    }

    fun authResult(): LiveData<Result<Unit>> = result

    companion object {
        val UNKNOWN_EXCEPTION = Exception("Firebase Auth unknown exception")
    }

}