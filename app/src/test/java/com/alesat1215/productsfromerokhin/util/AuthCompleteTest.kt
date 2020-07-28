package com.alesat1215.productsfromerokhin.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alesat1215.productsfromerokhin.util.FirebaseAuthComplete.Companion.UNKNOWN_EXCEPTION
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthCompleteTest {

    private val authComplete = FirebaseAuthComplete()
    @Mock
    private lateinit var task: Task<AuthResult>
    @Mock
    private lateinit var result: AuthResult
    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun onCompleteSuccess() {
        `when`(task.isSuccessful).thenReturn(true)
        `when`(result.user).thenReturn(firebaseUser)
        `when`(task.result).thenReturn(result)
        var result = Result.failure<Unit>(Throwable(UNKNOWN_EXCEPTION))
        authComplete.authResult().observeForever { result = it }
        authComplete.onComplete(task)
        assertTrue(result.isSuccess)
    }

    @Test
    fun onCompleteFailed() {
        `when`(task.isSuccessful).thenReturn(false)
        // Exception from db
        val exception = "exception not null"
        `when`(task.exception).thenReturn(Exception(exception))
        var result = Result.success(Unit)
        authComplete.authResult().observeForever { result = it }
        authComplete.onComplete(task)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.localizedMessage?.contains(exception) ?: false)
        // Unknown exception
        `when`(task.exception).thenReturn(null)
        authComplete.onComplete(task)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.localizedMessage?.contains(UNKNOWN_EXCEPTION) ?: false)
    }
}