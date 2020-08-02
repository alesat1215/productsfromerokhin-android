package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhoneRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var limiter: UpdateLimiter
    @Mock
    private lateinit var phoneDao: PhoneDao
    private val phoneForOrder = PhoneForOrder("phone")

    private lateinit var repository: PhoneRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(db.phoneDao()).thenReturn(phoneDao)
        `when`(db.phoneDao().phone()).thenReturn(MutableLiveData(phoneForOrder))
        `when`(firebaseRemoteConfig.getString(PhoneRepository.PHONE)).thenReturn(phoneForOrder.phone)
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        repository = PhoneRepository(remoteConfig, db, limiter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun phone() = runBlocking {
        // Not update db (limiter)
        `when`(limiter.needUpdate()).thenReturn(false)
        var result: PhoneForOrder? = null
        repository.phone().observeForever { result = it }
        sleep(100)
        assertEquals(result, phoneForOrder)
        sleep(100)
        verify(phoneDao, never()).updatePhone(phoneForOrder)
        // Not update db (result onFailure)
        result = null
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
        repository.phone().observeForever { result = it }
        sleep(100)
        assertEquals(result, phoneForOrder)
        sleep(100)
        verify(phoneDao, never()).updatePhone(phoneForOrder)
        // Update db
        result = null
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.phone().observeForever { result = it }
        sleep(100)
        assertEquals(result, phoneForOrder)
        sleep(100)
        verify(phoneDao).updatePhone(phoneForOrder)
    }
}