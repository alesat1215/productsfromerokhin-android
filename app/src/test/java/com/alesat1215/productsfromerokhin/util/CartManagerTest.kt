package com.alesat1215.productsfromerokhin.util

import com.alesat1215.productsfromerokhin.data.ProductInCart
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.ProductInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CartManagerTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private val productInCart = ProductInCart()
    private lateinit var productInfo: ProductInfo

    private lateinit var cartManager: CartManager

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        productInfo = ProductInfo(inCart = listOf(productInCart))
        cartManager = object : CartManager() {
            override val productsRepository = this@CartManagerTest.repository
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun addProductToCart() = runBlocking {
        cartManager.addProductToCart(productInfo)
        verify(repository).addProductToCart(productInfo.asProductInCart())
    }

    @Test
    fun delProductFromCart() = runBlockingTest {
        cartManager.delProductFromCart(productInfo)
        verify(repository).delProductFromCart(productInCart)
    }
}