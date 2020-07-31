package com.alesat1215.productsfromerokhin.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var productsDao: ProductsDao
    private lateinit var titlesDao: TitlesDao
    private lateinit var cartDao: CartDao
    private lateinit var profileDao: ProfileDao
    private lateinit var instructionsDao: InstructionsDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .build()
        productsDao = db.productsDao()
        titlesDao = db.titlesDao()
        cartDao = db.cartDao()
        profileDao = db.profileDao()
        instructionsDao = db.instructionsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun updateProducts() {
        var productsInfo: List<ProductInfo> = emptyList()
        var groups: List<Group> = emptyList()
        val product = Product(name = "product")
        val group = Group(name = "group")
        productsDao.products().observeForever { productsInfo = it }
        productsDao.groups().observeForever { groups = it }
        productsDao.updateProducts(listOf(group), listOf(product))
        assertEquals(groups.first(), group)
        assertEquals(productsInfo.first().product, product)
    }

    @Test
    fun updateTitles() {
        val titles = Titles(title = "title")
        var result: Titles? = null
        titlesDao.titles().observeForever { result = it }
        titlesDao.updateTitles(titles)
        assertEquals(result, titles)
    }

    @Test
    fun cartDao() {
        val product = Product(name = "product")
        val productInCart = ProductInCart(name = "product")
        var result: List<ProductInfo> = emptyList()
        productsDao.products().observeForever { result = it }
        productsDao.insertProducts(listOf(product))
        cartDao.insertProductInCart(productInCart)
        assertEquals(result.first().inCart.first().name, productInCart.name)
        cartDao.deleteProductFromCart(result.first().inCart.first())
        assertTrue(result.first().inCart.isEmpty())
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