package com.alesat1215.productsfromerokhin.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alesat1215.productsfromerokhin.data.local.*
import com.alesat1215.productsfromerokhin.remoteDataMockAndroidTest
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductsDatabaseTest2 {
    private lateinit var dao: ProductsDao
    private lateinit var db: ProductsDatabase
    private val data by lazy { remoteDataMockAndroidTest() }
    private val products by lazy { data.products().map {
        Product(it, listOf(ProductInCart(name = it.name)))
    } }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ProductsDatabase::class.java)
            .build()
        dao = db.productsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun updateAndRead() {
        var products = listOf<Product>()
        var groups = listOf<GroupDB>()
        var titles: Titles? = null
        dao.products().observeForever { products = it }
        dao.groups().observeForever { groups = it }
        dao.titles().observeForever { titles = it }
        dao.update(data)
        assertEquals(products, this.products)
        assertEquals(groups, data.groups())
        assertEquals(titles?.title, data.title)
        assertEquals(titles?.img, data.img)
        assertEquals(titles?.imgTitle, data.imgTitle)
        assertEquals(titles?.productsTitle, data.productsTitle)
        assertEquals(titles?.productsTitle2, data.productsTitle2)
    }
}