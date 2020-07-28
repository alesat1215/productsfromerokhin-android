package com.alesat1215.productsfromerokhin.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    @Mock
    private lateinit var firebaseUser: FirebaseUser
    @Mock
    private lateinit var task: Task<AuthResult>
    @Mock
    private lateinit var firebaseAuthComplete: FirebaseAuthComplete

    private lateinit var auth: Auth

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        auth = Auth(firebaseAuth, firebaseAuthComplete)
    }

    @Test
    fun signInAlready() {
        `when`(firebaseAuth.currentUser).thenReturn(firebaseUser)
        var result = Result.failure<Unit>(Throwable())
        auth.signIn().observeForever { result = it }
        assertTrue(result.isSuccess)
    }

    @Test
    fun signIn() {
        `when`(firebaseAuth.currentUser).thenReturn(null)
        `when`(firebaseAuth.signInAnonymously()).thenReturn(task)
        val result: LiveData<Result<Unit>> = MutableLiveData(Result.success(Unit))
        `when`(firebaseAuthComplete.authResult()).thenReturn(result)
        assertEquals(auth.signIn(), result)
    }
}