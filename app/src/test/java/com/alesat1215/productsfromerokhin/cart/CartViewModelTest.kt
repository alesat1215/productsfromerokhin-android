package com.alesat1215.productsfromerokhin.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.profileMockTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CartViewModelTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: CartViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(repository.productsInCart)
            .thenReturn(MutableLiveData(RemoteDataMockTest.productsNotEmptyCart))
        `when`(repository.profile).thenReturn(MutableLiveData(profileMockTest()))
        viewModel = CartViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun products() {
        var products = emptyList<Product>()
        viewModel.products().observeForever { products = it }
        assertEquals(products, RemoteDataMockTest.productsNotEmptyCart)
    }

    @Test
    fun totalInCart() {
        var total = 0
        viewModel.totalInCart().observeForever { total = it }
        assertEquals(total, RemoteDataMockTest.sumInProductsNotEmptyCart)
    }

    @Test
    fun order() {
        var order = ""
        viewModel.order().observeForever { order = it }
        RemoteDataMockTest.productsNotEmptyCart.forEach {
            assertTrue(order.contains(it.textForOrder()))
        }
    }

    @Test
    fun delivery() {
        var delivery = ""
        viewModel.delivery().observeForever { delivery = it }
        assertTrue(delivery.contains(profileMockTest().name))
        assertTrue(delivery.contains(profileMockTest().phone))
        assertTrue(delivery.contains(profileMockTest().address))
    }

    @Test
    fun clearCart() = runBlocking {
        viewModel.clearCart()
        verify(repository).clearCart()
    }
}