package com.alesat1215.productsfromerokhin.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.Task
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FirebaseOnCompleteTest {
    @Mock
    private lateinit var task: Task<Unit>
    private val firebaseOnComplete = FirebaseOnComplete<Unit>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun result() {
        val firebaseException = Exception("firebaseException")
        var result = Result.failure<Unit>(firebaseException)
        firebaseOnComplete.result().observeForever { result = it }
        // Success
        `when`(task.isSuccessful).thenReturn(true)
        firebaseOnComplete.onComplete(task)
        assertTrue(result.isSuccess)
        // Failed
        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.exception).thenReturn(firebaseException)
        firebaseOnComplete.onComplete(task)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.localizedMessage, firebaseException.localizedMessage)
        // Failed & exception from firebase null
        `when`(task.exception).thenReturn(null)
        firebaseOnComplete.onComplete(task)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.localizedMessage, FirebaseOnComplete.UNKNOWN_EXCEPTION)
    }
}