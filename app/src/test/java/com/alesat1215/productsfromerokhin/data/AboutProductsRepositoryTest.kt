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
class AboutProductsRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var aboutProductsDao: AboutProductsDao

    private val aboutProducts = arrayOf(AboutProducts())
    @Mock
    private lateinit var limiter: UpdateLimiter
    @Mock
    private lateinit var gson: Gson

    private lateinit var repository: AboutProductsRepository

    private lateinit var dbUpdater: DatabaseUpdater

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(db.aboutProductsDao()).thenReturn(aboutProductsDao)
        `when`(db.aboutProductsDao().aboutProducts()).thenReturn(MutableLiveData(aboutProducts.asList()))
        `when`(firebaseRemoteConfig.getString(AboutProductsRepository.ABOUT_PRODUCTS_LIST)).thenReturn("")
        `when`(gson.fromJson("", Array<AboutProducts>::class.java)).thenReturn(aboutProducts)
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        dbUpdater = DatabaseUpdater(limiter, remoteConfig)
        repository = AboutProductsRepository(db, dbUpdater, gson)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun aboutProducts() = runBlocking {
        // Not update db
        var result = emptyList<AboutProducts>()
        `when`(limiter.needUpdate()).thenReturn(false)
        repository.aboutProducts().observeForever { result = it }
        sleep(100)
        assertEquals(result, aboutProducts.asList())
        verify(aboutProductsDao, never()).updateAboutProducts(aboutProducts.asList())
        // Update db
        result = emptyList()
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.aboutProducts().observeForever { result = it }
        sleep(100)
        assertEquals(result, aboutProducts.asList())
        verify(aboutProductsDao).updateAboutProducts(aboutProducts.asList())
    }
}