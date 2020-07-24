package com.alesat1215.productsfromerokhin.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.profileMockTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CartViewModelTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: CartViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(repository.productsInCart)
            .thenReturn(MutableLiveData(RemoteDataMockTest.productsNotEmptyCart))
        `when`(repository.profile).thenReturn(MutableLiveData(profileMockTest()))
        viewModel = CartViewModel(repository)
    }

    @Test
    fun products() {
        var products = emptyList<Product>()
        viewModel.products().observeForever { products = it }
        assertEquals(products, RemoteDataMockTest.productsNotEmptyCart)
    }

    @Test
    fun totalInCart() {
    }

    @Test
    fun order() {
    }

    @Test
    fun delivery() {
    }

    @Test
    fun clearCart() {
    }
}