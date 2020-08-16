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
    private lateinit var contactsRepository: ContactsRepository
    @Mock
    private lateinit var aboutProductsRepository: AboutProductsRepository
    @Mock
    private lateinit var aboutAppRepository: AboutAppRepository
    @Mock
    private lateinit var instruction: Instruction
    private lateinit var instructions: List<Instruction>

    private val phoneForOrder = Contacts("phone")
    @Mock
    private lateinit var aboutProducts: AboutProducts
    private lateinit var aboutProductsList: List<AboutProducts>
    @Mock
    private lateinit var titlesRepository: TitlesRepository
    private val titles: LiveData<Titles?> = MutableLiveData(Titles())
    @Mock
    private lateinit var orderWarningRepository: OrderWarningRepository
    private val orderWarning: LiveData<OrderWarning?> = MutableLiveData(OrderWarning())
    @Mock
    private lateinit var auth: Auth
    private val authResult: LiveData<Result<Unit>> = MutableLiveData(Result.success(Unit))
    @Mock
    private lateinit var productInfo: ProductInfo
    private lateinit var products: List<ProductInfo>
    private val aboutApp = AboutApp()

    private lateinit var viewModel: LoadViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(auth.signIn()).thenReturn(authResult)
        products = listOf(productInfo)
        instructions = listOf(instruction)
        aboutProductsList = listOf(aboutProducts)
        `when`(productsRepository.products()).thenReturn(MutableLiveData(products))
        `when`(tutorialRepository.instructions()).thenReturn(MutableLiveData(instructions))
        `when`(contactsRepository.contacts()).thenReturn(MutableLiveData(phoneForOrder))
        `when`(titlesRepository.titles()).thenReturn(titles)
        `when`(aboutProductsRepository.aboutProducts()).thenReturn(MutableLiveData(aboutProductsList))
        `when`(aboutAppRepository.aboutApp()).thenReturn(MutableLiveData(aboutApp))
        `when`(orderWarningRepository.orderWarning()).thenReturn(orderWarning)
        viewModel = LoadViewModel(
            productsRepository,
            tutorialRepository,
            contactsRepository,
            titlesRepository,
            aboutProductsRepository,
            aboutAppRepository,
            orderWarningRepository,
            auth)
    }

    @Test
    fun firebaseAuth() {
        assertEquals(viewModel.firebaseAuth(), authResult)
    }

    @Test
    fun loadTutorialComplete() {
        var result = false
        viewModel.loadTutorialComplete().observeForever { result = it }
        assertTrue(result)
    }

    @Test
    fun loadDataComplete() {
        var result = false
        viewModel.loadDataComplete().observeForever { result = it }
        assertTrue(result)
    }

}