package com.alesat1215.productsfromerokhin.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    @Mock
    private lateinit var firebaseUser: FirebaseUser
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
        Mockito.`when`(firebaseAuth.currentUser).thenReturn(firebaseUser)
        var result = Result.failure<Unit>(Throwable())
        auth.signIn().observeForever { result = it }
        assertTrue(result.isSuccess)
    }
}