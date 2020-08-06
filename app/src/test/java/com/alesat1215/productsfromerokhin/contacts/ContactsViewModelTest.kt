package com.alesat1215.productsfromerokhin.contacts

import androidx.lifecycle.LiveData
import com.alesat1215.productsfromerokhin.data.Contacts
import com.alesat1215.productsfromerokhin.data.IContactsRepository
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContactsViewModelTest {
    @Mock
    private lateinit var repository: IContactsRepository
    @Mock
    private lateinit var contacts: LiveData<Contacts?>

    private lateinit var viewModel: ContactsViewModel

    @Before
    fun setUp() {
        `when`(repository.contacts()).thenReturn(contacts)
        viewModel = ContactsViewModel(repository)
    }

    @Test
    fun contacts() {
        assertEquals(viewModel.contacts, contacts)
    }
}