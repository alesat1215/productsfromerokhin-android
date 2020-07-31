package com.alesat1215.productsfromerokhin.menu

import androidx.lifecycle.LiveData
import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.ProductInfo
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MenuViewModelTest {
    @Mock
    private lateinit var repository: ProductsRepository
    @Mock
    private lateinit var products: LiveData<List<ProductInfo>>
    @Mock
    private lateinit var groups: LiveData<List<Group>>

    private lateinit var viewModel: MenuViewModel

    @Before
    fun setUp() {
        `when`(repository.groups()).thenReturn(groups)
        `when`(repository.products()).thenReturn(products)
        viewModel = MenuViewModel(repository)
    }

    @Test
    fun products() {
        assertEquals(viewModel.products(), products)
    }

    @Test
    fun groups() {
        assertEquals(viewModel.groups(), groups)
    }
}