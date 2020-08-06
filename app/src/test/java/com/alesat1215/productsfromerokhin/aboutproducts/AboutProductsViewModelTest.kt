package com.alesat1215.productsfromerokhin.aboutproducts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.AboutProducts
import com.alesat1215.productsfromerokhin.data.AboutProductsRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AboutProductsViewModelTest {
    @Mock
    private lateinit var aboutProductsRepository: AboutProductsRepository

    private val aboutProducts = listOf(AboutProducts(img = "img 1"), AboutProducts())
    private val predicate: (AboutProducts) -> Boolean = { it.img.isNotEmpty() }

    private lateinit var viewModel: AboutProductsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(aboutProductsRepository.aboutProducts()).thenReturn(MutableLiveData(aboutProducts))
        viewModel = AboutProductsViewModel(aboutProductsRepository)
    }

    @Test
    fun aboutProducts() {
        var result = emptyList<AboutProducts>()
        viewModel.aboutProducts(predicate).observeForever { result = it }
        assertEquals(result, aboutProducts.filter(predicate))
    }
}