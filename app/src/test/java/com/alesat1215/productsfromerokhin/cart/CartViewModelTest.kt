package com.alesat1215.productsfromerokhin.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.ProductInfo
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.Profile
import com.alesat1215.productsfromerokhin.data.ProfileRepository
import kotlinx.coroutines.*
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

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CartViewModelTest {
    @Mock
    private lateinit var productsRepository: ProductsRepository
    @Mock
    private lateinit var profileRepository: ProfileRepository
    @Mock
    private lateinit var productInfo: ProductInfo
    private lateinit var products: LiveData<List<ProductInfo>>
    private val priceSumInCart = 10
    private val textForOrder = "textForOrder"
    @Mock
    private lateinit var profile: Profile
    private val delivery = "delivery"

    private lateinit var viewModel: CartViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(productInfo.priceSumInCart()).thenReturn(priceSumInCart)
        `when`(productInfo.textForOrder()).thenReturn(textForOrder)
        products = MutableLiveData(listOf(productInfo))
        `when`(productsRepository.productsInCart).thenReturn(products)
        `when`(profile.delivery()).thenReturn(delivery)
        `when`(profileRepository.profile).thenReturn(MutableLiveData(profile))
        viewModel = CartViewModel(productsRepository, profileRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun products() {
        assertEquals(viewModel.products(), products)
    }

    @Test
    fun totalInCart() {
        var result = 0
        viewModel.totalInCart().observeForever { result = it }
        assertEquals(result, priceSumInCart)
    }

    @Test
    fun order() {
        var result = ""
        viewModel.order().observeForever { result = it }
        assertTrue(result.contains(textForOrder))
    }

    @Test
    fun delivery() {
        var result = ""
        viewModel.delivery().observeForever { result = it }
        assertEquals(result, delivery)
    }

    @Test
    fun clearCart() = runBlocking {
        viewModel.clearCart()
        verify(productsRepository).clearCart()
    }
}