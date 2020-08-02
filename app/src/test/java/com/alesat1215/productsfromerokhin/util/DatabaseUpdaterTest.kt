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

    private lateinit var databaseUpdater: DatabaseUpdater

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        databaseUpdater = object : DatabaseUpdater {
            override val limiter = this@DatabaseUpdaterTest.limiter
            override val remoteConfig = this@DatabaseUpdaterTest.remoteConfig
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun updateDB() {
        var insertData = false
        var result = Result.failure<Unit>(Exception())
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        databaseUpdater.updateDB { insertData = true }.observeForever { result = it }
        sleep(100)
        assertTrue(insertData)
        assertTrue(result.isSuccess)
    }
}