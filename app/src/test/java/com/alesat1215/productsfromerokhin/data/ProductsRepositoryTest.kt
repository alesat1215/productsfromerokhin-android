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
import org.junit.Assert.assertEquals
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductsRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase

    private lateinit var dbUpdater: DatabaseUpdater
    @Mock
    private lateinit var productsDao: ProductsDao
    @Mock
    private lateinit var cartDao: CartDao

    private val groups = arrayOf(Group())
    private val products = emptyList<Product>()
    private val productsInfo = listOf(ProductInfo(
        Product(), listOf(
        ProductInCart()
    )))
    @Mock
    private lateinit var productInCart: ProductInCart

    @Mock
    private lateinit var limiter: UpdateLimiter
    @Mock
    private lateinit var gson: Gson

    private lateinit var repository: ProductsRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(db.productsDao()).thenReturn(productsDao)
        `when`(db.productsDao().products()).thenReturn(MutableLiveData(productsInfo))
        `when`(db.productsDao().groups()).thenReturn(MutableLiveData(groups.asList()))
        `when`(db.cartDao()).thenReturn(cartDao)
        `when`(firebaseRemoteConfig.getString(ProductsRepository.PRODUCTS)).thenReturn("")
        `when`(gson.fromJson("", Array<Group>::class.java)).thenReturn(groups)
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        dbUpdater = DatabaseUpdater(limiter, remoteConfig)
        repository = ProductsRepository(db, dbUpdater, gson)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun products() = runBlocking {
        // Not update db
        var result: List<ProductInfo> = emptyList()
        `when`(limiter.needUpdate()).thenReturn(false)
        repository.products().observeForever { result = it }
        sleep(100)
        assertEquals(result, productsInfo)
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        // Update db
        result = emptyList()
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.products().observeForever { result = it }
        sleep(100)
        assertEquals(result, productsInfo)
        verify(productsDao).updateProducts(groups.asList(), products)
    }

    @Test
    fun groups() = runBlocking {
        // Not update db
        var result: List<Group> = emptyList()
        `when`(limiter.needUpdate()).thenReturn(false)
        repository.groups().observeForever { result = it }
        sleep(100)
        assertEquals(result, groups.asList())
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        // Update db
        result = emptyList()
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.groups().observeForever { result = it }
        sleep(100)
        assertEquals(result, groups.asList())
        verify(productsDao).updateProducts(groups.asList(), products)
    }

    @Test
    fun addProductToCart() = runBlocking {
        repository.addProductToCart(productInCart)
        verify(db.cartDao()).insertProductInCart(productInCart)
    }

    @Test
    fun delProductFromCart() = runBlocking {
        repository.delProductFromCart(productInCart)
        verify(db.cartDao()).deleteProductFromCart(productInCart)
    }

    @Test
    fun clearCart() = runBlocking {
        repository.clearCart()
        verify(db.cartDao()).clearCart()
    }

}