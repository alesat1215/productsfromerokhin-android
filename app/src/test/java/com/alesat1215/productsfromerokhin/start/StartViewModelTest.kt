package com.alesat1215.productsfromerokhin.start

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.ProductInfo
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.Titles
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StartViewModelTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private val titles = Titles("title", "img", "imgTitle", "productsTitle", "productsTitle2")
    private val products = listOf(ProductInfo(Product(name = "name"), emptyList()))
    private lateinit var viewModel: StartViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(repository.titles()).thenReturn(MutableLiveData(titles))
        `when`(repository.products()).thenReturn(MutableLiveData(products))
        viewModel = StartViewModel(repository)
    }

    @Test
    fun title() {
        var result: String? = null
        viewModel.title(StartTitle.TITLE).observeForever { result = it }
        assertEquals(result, titles.title)
        viewModel.title(StartTitle.IMG).observeForever { result = it }
        assertEquals(result, titles.img)
        viewModel.title(StartTitle.IMGTITLE).observeForever { result = it }
        assertEquals(result, titles.imgTitle)
        viewModel.title(StartTitle.PRODUCTS).observeForever { result = it }
        assertEquals(result, titles.productsTitle)
        viewModel.title(StartTitle.PRODUCTS2).observeForever { result = it }
        assertEquals(result, titles.productsTitle2)
    }

    @Test
    fun products() {
        var result: List<ProductInfo> = emptyList()
        viewModel.products().observeForever { result = it }
        assertEquals(result, products)
        viewModel.products { it.product?.inStart2 == true }.observeForever { result = it }
        assertTrue(result.isEmpty())
        viewModel.products { it.product?.name == "name" }.observeForever { result = it }
        assertEquals(result, products)
    }
}