package com.alesat1215.productsfromerokhin.start

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(repository.titles()).thenReturn(MutableLiveData(RemoteDataMockTest.data.titles()))
        `when`(repository.products()).thenReturn(MutableLiveData(RemoteDataMockTest.productsNotEmptyCart))
        viewModel = StartViewModel(repository)
    }

    @Test
    fun title() {
        var title: String? = null
        viewModel.title(StartTitle.TITLE).observeForever { title = it }
        assertEquals(title, RemoteDataMockTest.data.title)
        viewModel.title(StartTitle.IMG).observeForever { title = it }
        assertEquals(title, RemoteDataMockTest.data.img)
        viewModel.title(StartTitle.IMGTITLE).observeForever { title = it }
        assertEquals(title, RemoteDataMockTest.data.imgTitle)
        viewModel.title(StartTitle.PRODUCTS).observeForever { title = it }
        assertEquals(title, RemoteDataMockTest.data.productsTitle)
        viewModel.title(StartTitle.PRODUCTS2).observeForever { title = it }
        assertEquals(title, RemoteDataMockTest.data.productsTitle2)
    }

    @Test
    fun products() {
        var products = listOf<Product>()
        viewModel.products().observeForever { products = it }
        assertEquals(products, RemoteDataMockTest.productsNotEmptyCart)
        val predicate: (Product) -> Boolean = { it.productDB?.name == "product_1" }
        viewModel.products(predicate).observeForever { products = it }
        assertEquals(products, RemoteDataMockTest.productsNotEmptyCart.filter(predicate))
    }
}