package com.alesat1215.productsfromerokhin.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest2 {
    private lateinit var dao: ProductsDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .build()
        dao = db.productsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

//    @Test
//    fun updateReadClearProducts() {
//        var products = listOf<Product>()
//        var groups = listOf<GroupDB>()
//        var titles: Titles? = null
//        dao.products().observeForever { products = it }
//        dao.groups().observeForever { groups = it }
//        dao.titles().observeForever { titles = it }
//        dao.update(RemoteDataMockAndroidTest.data)
//        assertEquals(products, RemoteDataMockAndroidTest.productsEmptyCart)
//        assertEquals(groups, RemoteDataMockAndroidTest.data.groups())
//        assertEquals(titles?.title, RemoteDataMockAndroidTest.data.title)
//        assertEquals(titles?.img, RemoteDataMockAndroidTest.data.img)
//        assertEquals(titles?.imgTitle, RemoteDataMockAndroidTest.data.imgTitle)
//        assertEquals(titles?.productsTitle, RemoteDataMockAndroidTest.data.productsTitle)
//        assertEquals(titles?.productsTitle2, RemoteDataMockAndroidTest.data.productsTitle2)
//        dao.clearBeforeUpdate()
//        assertTrue(products.isEmpty())
//        assertTrue(groups.isEmpty())
//        assertNull(titles)
//    }
//
//    @Test
//    fun insertDeleteClearCart() {
//        var products = listOf<Product>()
//        // Insert
//        dao.products().observeForever { products = it }
//        dao.update(RemoteDataMockAndroidTest.data)
//        assertTrue(products.isNotEmpty())
//        products.forEach { assertTrue(it.inCart.isEmpty()) }
//        RemoteDataMockAndroidTest.productsForCart.forEach { dao.insertProductInCart(it) }
//        products.forEach { assertTrue(it.inCart.isNotEmpty()) }
//        // Delete
//        dao.deleteProductFromCart(products.first().inCart.first())
//        assertTrue(products.first().inCart.isEmpty())
//        // Clear
//        dao.clearCart()
//        products.forEach { assertTrue(it.inCart.isEmpty()) }
//    }
//
//    @Test
//    fun updateProfile() {
//        val profile = profileMockAndroidTest()
//        var result: Profile? = null
//        dao.profile().observeForever { result = it }
//        dao.updateProfile(profile)
//        assertEquals(result?.name, profile.name)
//        assertEquals(result?.phone, profile.phone)
//        assertEquals(result?.address, profile.address)
//    }
}