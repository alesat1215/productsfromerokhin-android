package com.alesat1215.productsfromerokhin.start

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.RemoteData
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.remoteDataMockTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StartViewModelTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: StartViewModel
    private val data by lazy { remoteDataMockTest() }
    private val products by lazy { data.products().map {
        Product(it.group, it.name, it.consist, it.img, it.price, it.inStart, it.inStart2)
    } }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(repository.titles()).thenReturn(MutableLiveData(data.titles()))
        `when`(repository.products()).thenReturn(MutableLiveData(products))
        viewModel = StartViewModel(repository)
    }

    @Test
    fun title() {
        var title: String? = null
        viewModel.title(StartTitle.TITLE).observeForever { title = it }
        assertEquals(title, data.title)
        viewModel.title(StartTitle.IMG).observeForever { title = it }
        assertEquals(title, data.img)
        viewModel.title(StartTitle.IMGTITLE).observeForever { title = it }
        assertEquals(title, data.imgTitle)
        viewModel.title(StartTitle.PRODUCTS).observeForever { title = it }
        assertEquals(title, data.productsTitle)
        viewModel.title(StartTitle.PRODUCTS2).observeForever { title = it }
        assertEquals(title, data.productsTitle2)
    }

    @Test
    fun products() {
        var products = listOf<Product>()
        viewModel.products().observeForever { products = it }
        assertEquals(products, this.products)
        val predicate: (Product) -> Boolean = { it.name == "product_1" }
        viewModel.products(predicate).observeForever { products = it }
        assertEquals(products, this.products.filter(predicate))
    }
}