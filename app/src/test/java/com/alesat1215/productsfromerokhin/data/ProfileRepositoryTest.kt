package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileRepositoryTest {
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var profileDao: ProfileDao
    private val profile = MutableLiveData(mock(Profile::class.java))

    private lateinit var repository: ProfileRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(db.profileDao()).thenReturn(profileDao)
        `when`(db.profileDao().profile()).thenReturn(profile)
        repository = ProfileRepository(db)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun profile() {
        assertEquals(repository.profile, profile)
    }

    @Test
    fun updateProfile() {
    }
}