package com.alesat1215.productsfromerokhin.load

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.Instruction
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.TutorialRepository
import com.alesat1215.productsfromerokhin.data.ProductInfo
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
    fun firebaseAuth() {
        val result: LiveData<Result<Unit>> = MutableLiveData(Result.success(Unit))
        `when`(auth.signIn()).thenReturn(result)
        assertEquals(viewModel.firebaseAuth(), result)
    }

    @Test
    fun loadCompleteProducts() {
        // Repository return empty list of products
        `when`(repository.products())
            .thenReturn(MutableLiveData(emptyList()))
        var result = true
        viewModel.loadCompleteProducts().observeForever { result = it }
        assertFalse(result)
        // Repository return not empty list of products
        `when`(repository.products())
            .thenReturn(MutableLiveData(listOf(
                ProductInfo(
                    inCart = emptyList()
                )
            )))
        result = false
        viewModel.loadCompleteProducts().observeForever { result = it }
        assertTrue(result)
    }

    @Test
    fun loadCompleteTutorialEmptyData() {
        // Repository return empty list of instructions
        `when`(tutorialRepository.instructions()).thenReturn(MutableLiveData(emptyList<Instruction>()))
        var result = true
        viewModel.loadCompleteTutorial().observeForever { result = it }
        assertFalse(result)
        // Repository return empty list of instructions
        `when`(tutorialRepository.instructions()).thenReturn(MutableLiveData(listOf(Instruction())))
        result = false
        viewModel.loadCompleteTutorial().observeForever { result = it }
        assertTrue(result)
    }

}