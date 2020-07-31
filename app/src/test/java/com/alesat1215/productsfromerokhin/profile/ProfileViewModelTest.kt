package com.alesat1215.productsfromerokhin.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.Profile
import com.alesat1215.productsfromerokhin.data.ProfileRepository
import com.alesat1215.productsfromerokhin.profileMockTest
import kotlinx.coroutines.*
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

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {
    @Mock
    private lateinit var repository: ProfileRepository
    private lateinit var viewModel: ProfileViewModel
    @Mock
    private lateinit var profile: LiveData<Profile>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(repository.profile).thenReturn(profile)
        viewModel = ProfileViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun profile() {
        assertEquals(viewModel.profile, profile)
    }

    @Test
    fun updateProfile() = runBlocking {
        val profile = profileMockTest()
        `when`(repository.updateProfile(profile)).thenReturn(Unit)
        viewModel.updateProfile(profile.name, profile.phone, profile.address)
        verify(repository).updateProfile(profile)
    }
}