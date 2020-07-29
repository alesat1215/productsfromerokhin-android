package com.alesat1215.productsfromerokhin.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteConfigTest {
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var firebaseOnComplete: FirebaseOnComplete<Boolean>
    @Mock
    private lateinit var task: Task<Boolean>
    private val result: LiveData<Result<Unit>> = MutableLiveData(Result.success(Unit))

    private lateinit var remoteConfig: RemoteConfig

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        remoteConfig = RemoteConfig(firebaseRemoteConfig, firebaseOnComplete)
        `when`(task.addOnCompleteListener(firebaseOnComplete)).thenReturn(task)
        `when`(firebaseRemoteConfig.fetchAndActivate()).thenReturn(task)
        `when`(firebaseOnComplete.result()).thenReturn(result)
    }

    @Test
    fun fetchAndActivate() {
        assertEquals(remoteConfig.fetchAndActivate(), result)
    }
}