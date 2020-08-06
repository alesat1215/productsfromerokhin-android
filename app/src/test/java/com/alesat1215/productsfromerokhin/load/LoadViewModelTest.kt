package com.alesat1215.productsfromerokhin.load

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.*
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
    private lateinit var productsRepository: ProductsRepository
    @Mock
    private lateinit var tutorialRepository: TutorialRepository
    @Mock
    private lateinit var phoneRepository: PhoneRepository
    @Mock
    private lateinit var aboutProductsRepository: AboutProductsRepository
    @Mock
    private lateinit var instruction: Instruction
    private lateinit var instructions: List<Instruction>
    @Mock
    private lateinit var titlesRepository: TitlesRepository
    @Mock
    private lateinit var auth: Auth
    private val authResult: LiveData<Result<Unit>> = MutableLiveData(Result.success(Unit))
    @Mock
    private lateinit var productInfo: ProductInfo
    private lateinit var products: List<ProductInfo>

    private lateinit var viewModel: LoadViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(auth.signIn()).thenReturn(authResult)
        products = listOf(productInfo)
        instructions = listOf(instruction)
        `when`(productsRepository.products()).thenReturn(MutableLiveData(products))
        `when`(tutorialRepository.instructions()).thenReturn(MutableLiveData(instructions))
        viewModel = LoadViewModel(productsRepository, tutorialRepository, phoneRepository, titlesRepository, aboutProductsRepository, auth)
    }

    @Test
    fun firebaseAuth() {
        assertEquals(viewModel.firebaseAuth(), authResult)
    }

    @Test
    fun loadCompleteProducts() {
        var result = false
        viewModel.loadCompleteProducts().observeForever { result = it }
        assertTrue(result)
    }

    @Test
    fun loadCompleteTutorial() {
        var result = false
        viewModel.loadCompleteTutorial().observeForever { result = it }
        assertTrue(result)
    }

}