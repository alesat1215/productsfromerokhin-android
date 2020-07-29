package com.alesat1215.productsfromerokhin.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.Profile
import com.alesat1215.productsfromerokhin.data.ProfileRepository
import com.alesat1215.productsfromerokhin.profileMockTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {
    @Mock
    private lateinit var repository: ProfileRepository
    private lateinit var viewModel: ProfileViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(repository.profile).thenReturn(MutableLiveData(profileMockTest()))
        viewModel = ProfileViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun profile() {
        var profile: Profile? = null
        viewModel.profile.observeForever { profile = it }
        assertEquals(profile, profileMockTest())
    }

    @Test
    fun updateProfile() = runBlocking {
        val profile = profileMockTest()
        `when`(repository.updateProfile(profile)).thenReturn(Unit)
        viewModel.updateProfile(profile.name, profile.phone, profile.address)
        verify(repository).updateProfile(profile)
    }
}