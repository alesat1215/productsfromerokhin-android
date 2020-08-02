package com.alesat1215.productsfromerokhin.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import java.lang.Thread.sleep

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DatabaseUpdaterTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var limiter: UpdateLimiter

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var dbUpdater: DatabaseUpdater

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        dbUpdater = DatabaseUpdater(limiter, remoteConfig)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun needUpdate() {
        // Not update (limiter)
        var result = Result.success(Unit)
        `when`(limiter.needUpdate()).thenReturn(false)
        dbUpdater.needUpdate().observeForever { result = it }
        sleep(100)
        assertTrue(result.isFailure)
        // Not update (fetch failed)
        result = Result.success(Unit)
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
        dbUpdater.needUpdate().observeForever { result = it }
        sleep(100)
        assertTrue(result.isFailure)
    }

    @Test
    fun updateDB() {
//        var insertData = false
//        var result = Result.failure<Unit>(Exception())
//        // Not update db (limiter)
//        `when`(limiter.needUpdate()).thenReturn(false)
//        dbUpdater.updateDatabase { insertData = true }.observeForever { result = it }
//        sleep(100)
//        assertFalse(insertData)
//        assertTrue(result.isSuccess)
//        // Not update db (fetch failed)
//        insertData = false
//        result = Result.success(Unit)
//        `when`(limiter.needUpdate()).thenReturn(true)
//        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
//        dbUpdater.updateDatabase { insertData = true }.observeForever { result = it }
//        sleep(100)
//        assertFalse(insertData)
//        assertTrue(result.isFailure)
//        // Update db
//        `when`(limiter.needUpdate()).thenReturn(true)
//        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
//        dbUpdater.updateDatabase { insertData = true }.observeForever { result = it }
//        sleep(100)
//        assertTrue(insertData)
//        assertTrue(result.isSuccess)
    }
}