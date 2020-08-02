package com.alesat1215.productsfromerokhin.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alesat1215.productsfromerokhin.DataMock
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var productsDao: ProductsDao
    private lateinit var titlesDao: TitlesDao
    private lateinit var cartDao: CartDao
    private lateinit var profileDao: ProfileDao
    private lateinit var instructionsDao: InstructionsDao
    private lateinit var phoneDao: PhoneDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .setQueryExecutor(Dispatchers.Main.asExecutor())
            .setTransactionExecutor(Dispatchers.Main.asExecutor())
            .build()
        productsDao = db.productsDao()
        titlesDao = db.titlesDao()
        cartDao = db.cartDao()
        profileDao = db.profileDao()
        instructionsDao = db.instructionsDao()
        phoneDao = db.phoneDao()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
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

    @Test
    fun phoneDao() = runBlocking {
        var result: PhoneForOrder? = null
        phoneDao.phone().observeForever { result = it }
        phoneDao.updatePhone(DataMock.phone)
        sleep(100)
        assertEquals(result, DataMock.phone)
    }

}