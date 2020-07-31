package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
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
    @Mock
    private lateinit var productsDao: ProductsDao
    @Mock
    private lateinit var titlesDao: TitlesDao
    @Mock
    private lateinit var cartDao: CartDao

    private val groups = arrayOf(Group())
    private val titles = Titles()
    private val products = emptyList<Product>()
    private val productsInfo = listOf(ProductInfo(
        Product(), listOf(
        ProductInCart()
    )))
    private val productInCart = ProductInCart()

    @Mock
    private lateinit var limiter: RateLimiter
    @Mock
    private lateinit var gson: Gson

    private lateinit var repository: ProductsRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
//        `when`(dbFBFetchLimit.shouldFetch()).thenReturn(true)
        `when`(db.productsDao()).thenReturn(productsDao)
        `when`(db.productsDao().products()).thenReturn(MutableLiveData(productsInfo))
        `when`(db.productsDao().groups()).thenReturn(MutableLiveData(groups.asList()))
        `when`(db.titlesDao()).thenReturn(titlesDao)
        `when`(db.titlesDao().titles()).thenReturn(MutableLiveData(titles))
        `when`(db.cartDao()).thenReturn(cartDao)
        `when`(firebaseRemoteConfig.getString(ProductsRepository.PRODUCTS)).thenReturn("")
        `when`(firebaseRemoteConfig.getString(ProductsRepository.TITLES)).thenReturn("")
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        `when`(gson.fromJson("", Array<Group>::class.java)).thenReturn(groups)
        `when`(gson.fromJson("", Titles::class.java)).thenReturn(titles)
        repository = ProductsRepository(remoteConfig, db, limiter, gson)
//        `when`(db.productsDao().groups()).thenReturn(MutableLiveData(RemoteDataMockTest.data.groups()))
//        `when`(db.productsDao().titles()).thenReturn(MutableLiveData(RemoteDataMockTest.data.titles()))
//        `when`(db.productsDao().profile()).thenReturn(MutableLiveData(profileMockTest()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun products() {
        // Not update db (limiter)
        `when`(limiter.shouldFetch()).thenReturn(false)
        var result: List<ProductInfo> = emptyList()
        repository.products().observeForever { result = it }
        assertEquals(result, productsInfo)
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        verify(db.titlesDao(), never()).updateTitles(titles)
        // Not update db (result onFailure)
        result = emptyList()
        `when`(limiter.shouldFetch()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
        repository.products().observeForever { result = it }
        assertEquals(result, productsInfo)
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        verify(db.titlesDao(), never()).updateTitles(titles)
        // Update db
        result = emptyList()
        `when`(limiter.shouldFetch()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.products().observeForever { result = it }
        assertEquals(result, productsInfo)
        sleep(100)
        verify(db.productsDao()).updateProducts(groups.asList(), products)
        verify(db.titlesDao()).updateTitles(titles)
    }

    @Test
    fun titles() {
        // Not update db (limiter)
        `when`(limiter.shouldFetch()).thenReturn(false)
        var result: Titles? = null
        repository.titles().observeForever { result = it }
        assertEquals(result, titles)
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        verify(titlesDao, never()).updateTitles(titles)
        // Not update db (result onFailure)
        result = null
        `when`(limiter.shouldFetch()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
        repository.titles().observeForever { result = it }
        assertEquals(result, titles)
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        verify(titlesDao, never()).updateTitles(titles)
        // Update db
        result = null
        `when`(limiter.shouldFetch()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.titles().observeForever { result = it }
        assertEquals(result, titles)
        sleep(100)
        verify(db.productsDao()).updateProducts(groups.asList(), products)
        verify(db.titlesDao()).updateTitles(titles)
    }

    @Test
    fun groups() {
        // Not update db (limiter)
        `when`(limiter.shouldFetch()).thenReturn(false)
        var result: List<Group> = emptyList()
        repository.groups().observeForever { result = it }
        assertEquals(groups.asList(), result)
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        verify(titlesDao, never()).updateTitles(titles)
        // Not update db (result onFailure)
        result = emptyList()
        `when`(limiter.shouldFetch()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
        repository.groups().observeForever { result = it }
        assertEquals(groups.asList(), result)
        verify(productsDao, never()).updateProducts(groups.asList(), products)
        verify(titlesDao, never()).updateTitles(titles)
        // Update db
        result = emptyList()
        `when`(limiter.shouldFetch()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.groups().observeForever { result = it }
        assertEquals(groups.asList(), result)
        sleep(100)
        verify(db.productsDao()).updateProducts(groups.asList(), products)
        verify(db.titlesDao()).updateTitles(titles)
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

    //    @Test
//    fun productsGroupsTitles() {
//        val repo = ProductsRepository(authFBMock, dbFB, db, dbFBFetchLimit)
//        var products = emptyList<Product>()
//        var productsInCart = emptyList<Product>()
//        var groups = emptyList<GroupDB>()
//        var titles: Titles? = null
//        repo.products().observeForever { products = it }
//        repo.productsInCart.observeForever { productsInCart = it }
//        repo.groups().observeForever { groups = it }
//        repo.titles().observeForever { titles = it }
//        assertEquals(products, RemoteDataMockTest.productsNotEmptyCart)
//        assertEquals(productsInCart, RemoteDataMockTest.productsNotEmptyCart)
//        assertEquals(groups, RemoteDataMockTest.data.groups())
//        assertEquals(titles, RemoteDataMockTest.data.titles())
//    }
//
//    @Test
//    fun cart() = runBlocking {
//        val repo = ProductsRepository(authFBMock, dbFB, db, dbFBFetchLimit)
//        val product = ProductInCart()
//        repo.addProductToCart(product)
//        verify(db.productsDao()).insertProductInCart(product)
//        repo.delProductFromCart(product)
//        verify(db.productsDao()).deleteProductFromCart(product)
//        repo.clearCart()
//        verify(db.productsDao()).clearCart()
//    }
//
//    @Test
//    fun profile() = runBlocking {
//        val repo = ProductsRepository(authFBMock, dbFB, db, dbFBFetchLimit)
//        // Get profile
//        var profile: Profile? = null
//        repo.profile.observeForever { profile = it }
//        assertEquals(profile, profileMockTest())
//        // Update profile
//        repo.updateProfile(profile!!)
//        verify(db.productsDao()).updateProfile(profile!!)
//    }
}