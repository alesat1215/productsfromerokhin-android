package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileRepositoryTest {
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var profileDao: ProfileDao
    @Mock
    private lateinit var profile: Profile
    private lateinit var profileResult: LiveData<Profile?>

    private lateinit var repository: ProfileRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        profileResult = MutableLiveData(profile)
        `when`(db.profileDao()).thenReturn(profileDao)
        `when`(db.profileDao().profile()).thenReturn(profileResult)
        repository = ProfileRepository(db)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun profile() {
        assertEquals(repository.profile, profileResult)
    }

    @Test
    fun updateProfile() = runBlocking {
        repository.updateProfile(profile)
        verify(db.profileDao()).updateProfile(profile)
    }
}