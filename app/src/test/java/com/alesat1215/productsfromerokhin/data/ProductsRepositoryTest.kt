package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import com.alesat1215.productsfromerokhin.data.local.*
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsRepositoryTest {

    @Mock
    private lateinit var authFBMock: FirebaseAuth
//    @Mock
//    private lateinit var dbFB: DatabaseReference
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var dbFBFetchLimit: RateLimiter

    private var productInCart = false

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

//    @Before
//    fun setUp() {
//        `when`(authFBMock.currentUser).thenReturn(mock(FirebaseUser::class.java))
//        `when`(dbFB.database).thenReturn(mock(FirebaseDatabase::class.java))
//        `when`(dbFBFetchLimit.shouldFetch()).thenReturn(true)
//        `when`(db.productsDao()).thenReturn(mock(ProductsDao::class.java))
//        `when`(db.productsDao().products()).thenReturn(MutableLiveData(RemoteDataMockTest.productsNotEmptyCart))
//        `when`(db.productsDao().groups()).thenReturn(MutableLiveData(RemoteDataMockTest.data.groups()))
//        `when`(db.productsDao().titles()).thenReturn(MutableLiveData(RemoteDataMockTest.data.titles()))
//        `when`(db.productsDao().profile()).thenReturn(MutableLiveData(profileMockTest()))
//    }
//
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