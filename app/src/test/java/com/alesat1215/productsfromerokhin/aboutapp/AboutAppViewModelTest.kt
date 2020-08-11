package com.alesat1215.productsfromerokhin.aboutapp

import androidx.lifecycle.LiveData
import com.alesat1215.productsfromerokhin.data.AboutApp
import com.alesat1215.productsfromerokhin.data.AboutAppRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AboutAppViewModelTest {
    @Mock
    private lateinit var aboutAppRepository: AboutAppRepository
    @Mock
    private lateinit var aboutApp: LiveData<AboutApp?>
    private lateinit var viewModel: AboutAppViewModel

    @Before
    fun setUp() {
        `when`(aboutAppRepository.aboutApp()).thenReturn(aboutApp)
        viewModel = AboutAppViewModel(aboutAppRepository)
    }

    @Test
    fun aboutApp() {
        assertEquals(aboutApp, viewModel.aboutApp)
    }
}