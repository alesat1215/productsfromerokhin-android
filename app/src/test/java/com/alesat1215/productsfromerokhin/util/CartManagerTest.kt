package com.alesat1215.productsfromerokhin.util

import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CartManagerTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private lateinit var cartManager: CartManager
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        cartManager = object : CartManager() {
            override val repository = this@CartManagerTest.repository
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun addProductToCart() = runBlocking {
        val product = Product(inCart = emptyList())
        `when`(repository.addProductToCart(product.asProductInCart())).thenReturn(Unit)
        cartManager.addProductToCart(product)
        verify(repository).addProductToCart(product.asProductInCart())
    }

    @Test
    fun delProductFromCart() {
    }
}