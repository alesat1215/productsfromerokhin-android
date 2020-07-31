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

    @Test
    fun updateProfile() {
        val profile = Profile(name = "name")
        var result: Profile? = null
        profileDao.profile().observeForever { result = it }
        profileDao.updateProfile(profile)
        assertEquals(result?.name, profile.name)
    }

    @Test
    fun updateInstructions() {
        val instructions = listOf(Instruction(title = "title"))
        var result: List<Instruction> = emptyList()
        instructionsDao.instructions().observeForever { result = it }
        instructionsDao.updateInstructions(instructions)
        assertEquals(result, instructions)
    }

}