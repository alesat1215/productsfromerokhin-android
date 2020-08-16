package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
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
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OrderWarningRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase

    private lateinit var dbUpdater: DatabaseUpdater
    @Mock
    private lateinit var orderWarningDao: OrderWarningDao
    @Mock
    private lateinit var limiter: UpdateLimiter
    @Mock
    private lateinit var gson: Gson

    private val orderWarning = OrderWarning()

    private lateinit var repository: OrderWarningRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(db.orderWarningDao()).thenReturn(orderWarningDao)
        `when`(db.orderWarningDao().orderWarning()).thenReturn(MutableLiveData(orderWarning))
        `when`(firebaseRemoteConfig.getString(OrderWarningRepository.ORDER_WARNING)).thenReturn("")
        `when`(gson.fromJson("", OrderWarning::class.java)).thenReturn(orderWarning)
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        dbUpdater = DatabaseUpdater(limiter, remoteConfig)
        repository = OrderWarningRepository(db, dbUpdater, gson)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun orderWarning() = runBlocking {
        // Not update db
        var result: OrderWarning? = null
        `when`(limiter.needUpdate()).thenReturn(false)
        repository.orderWarning().observeForever { result = it }
        sleep(100)
        assertEquals(result, orderWarning)
        verify(orderWarningDao, never()).updateOrderWarning(orderWarning)
        // Update db
        result = null
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.orderWarning().observeForever { result = it }
        sleep(100)
        assertEquals(result, orderWarning)
        verify(orderWarningDao).updateOrderWarning(orderWarning)
    }
}