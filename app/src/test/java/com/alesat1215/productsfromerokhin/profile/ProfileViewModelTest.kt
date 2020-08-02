package com.alesat1215.productsfromerokhin.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.Profile
import com.alesat1215.productsfromerokhin.data.ProfileRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {
    @Mock
    private lateinit var repository: ProfileRepository
    private lateinit var viewModel: ProfileViewModel
    private val profile = Profile(name = "name", phone = "phone", address = "address")
    private lateinit var profileResult: LiveData<Profile?>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        profileResult = MutableLiveData(profile)
        `when`(repository.profile).thenReturn(profileResult)
        viewModel = ProfileViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun profile() {
        assertEquals(viewModel.profile, profileResult)
    }

    @Test
    fun updateProfile() = runBlocking {
        viewModel.updateProfile(profile.name, profile.phone, profile.address)
        sleep(100)
        verify(repository).updateProfile(profile)
    }
}