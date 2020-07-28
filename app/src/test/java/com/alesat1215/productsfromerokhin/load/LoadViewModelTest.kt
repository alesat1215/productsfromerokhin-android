package com.alesat1215.productsfromerokhin.load

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
//import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.TutorialRepository
import com.alesat1215.productsfromerokhin.util.Auth
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
    @Mock
    private lateinit var tutorialRepository: TutorialRepository
    @Mock
    private lateinit var auth: Auth
    private lateinit var viewModel: LoadViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = LoadViewModel(repository, tutorialRepository, auth)
    }

    @Test
    fun loadCompleteEmptyData() {
        // Repository return empty list of products
        `when`(repository.products())
            .thenReturn(MutableLiveData(emptyList()))
        var result = true
        viewModel.loadCompleteProducts().observeForever { result = it }
        assertFalse(result)
    }

//    @Test
//    fun loadCompleteNotEmptyData() {
//        // Repository return not empty list of products
//        `when`(repository.products())
//            .thenReturn(MutableLiveData(RemoteDataMockTest.productsNotEmptyCart))
//        var result = false
//        viewModel.loadCompleteProducts().observeForever { result = it }
//        assertTrue(result)
//    }
}