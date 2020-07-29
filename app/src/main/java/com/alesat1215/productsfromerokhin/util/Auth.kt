package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.logger.Logger
import javax.inject.Inject
import javax.inject.Singleton

/** Sign in to FirebaseAuth */
@Singleton
class Auth @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseOnComplete: FirebaseOnComplete<AuthResult>
) {
    /** @return result of sign in */
    fun signIn(): LiveData<Result<Unit>> {
        // Already sign in
        if (firebaseAuth.currentUser != null) {
            Logger.d("Already sign in: ${firebaseAuth.currentUser}")
            return MutableLiveData(Result.success(Unit))
        }
        // Sign in as anonymous
        firebaseAuth.signInAnonymously().addOnCompleteListener(firebaseOnComplete)
        return firebaseOnComplete.result()
    }
}