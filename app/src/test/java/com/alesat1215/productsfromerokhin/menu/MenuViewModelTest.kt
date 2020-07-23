package com.alesat1215.productsfromerokhin.menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.local.GroupDB
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
class MenuViewModelTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: MenuViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(repository.groups()).thenReturn(MutableLiveData(RemoteDataMockTest.data.groups()))
        `when`(repository.products()).thenReturn(MutableLiveData(RemoteDataMockTest.productsNotEmptyCart))
        viewModel = MenuViewModel(repository)
    }

    @Test
    fun products() {
        var products = listOf<Product>()
        viewModel.products().observeForever { products = it }
        assertEquals(products, RemoteDataMockTest.productsNotEmptyCart)
    }

    @Test
    fun groups() {
        var groups = listOf<GroupDB>()
        viewModel.groups().observeForever { groups = it }
        assertEquals(groups, RemoteDataMockTest.data.groups())
    }
}