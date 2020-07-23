package com.alesat1215.productsfromerokhin.load

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.remoteDataMockTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadViewModelTest {
    @Mock
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: LoadViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = LoadViewModel(repository)
    }

    @Test
    fun loadCompleteEmptyData() {
        // Repository return empty list of products
        `when`(repository.products())
            .thenReturn(MutableLiveData(emptyList()))
        var result = true
        viewModel.loadComplete().observeForever { result = it }
        assertFalse(result)
    }

    @Test
    fun loadCompleteNotEmptyData() {
        // Repository return not empty list of products
        `when`(repository.products())
            .thenReturn(MutableLiveData(RemoteDataMockTest.productsNotEmptyCart))
        var result = false
        viewModel.loadComplete().observeForever { result = it }
        assertTrue(result)
    }
}