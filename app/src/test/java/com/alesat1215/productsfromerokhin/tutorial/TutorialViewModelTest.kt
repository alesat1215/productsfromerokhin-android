package com.alesat1215.productsfromerokhin.tutorial

import androidx.lifecycle.LiveData
import com.alesat1215.productsfromerokhin.data.Instruction
import com.alesat1215.productsfromerokhin.data.TutorialRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TutorialViewModelTest {
    @Mock
    private lateinit var repository: TutorialRepository
    @Mock
    private lateinit var instructions: LiveData<List<Instruction>>

    private lateinit var viewModel: TutorialViewModel

    @Before
    fun setUp() {
        `when`(repository.instructions()).thenReturn(instructions)
        viewModel = TutorialViewModel(repository)
    }

    @Test
    fun instructions() {
        assertEquals(viewModel.instructions(), instructions)
    }
}