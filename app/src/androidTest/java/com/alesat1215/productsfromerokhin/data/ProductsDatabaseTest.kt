package com.alesat1215.productsfromerokhin.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alesat1215.productsfromerokhin.liveDataValue
import com.alesat1215.productsfromerokhin.remoteDataMock
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductsDatabaseTest {
    private lateinit var dao: ProductsDao
    private lateinit var db: ProductsDatabase

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
        val data = remoteDataMock()
        dao.update(data)
        val products = liveDataValue(dao.products())
        assertEquals(products, data.productsWithGroupId())
        val groups = liveDataValue(dao.groups())
        assertEquals(groups.map { it.id }, data.groups?.map { it.id })
        val titles = liveDataValue(dao.titles())
        assertEquals(titles?.title, data.title)
        assertEquals(titles?.img, data.img)
        assertEquals(titles?.imgTitle, data.imgTitle)
        assertEquals(titles?.productsTitle, data.productsTitle)
        assertEquals(titles?.productsTitle2, data.productsTitle2)
    }
}