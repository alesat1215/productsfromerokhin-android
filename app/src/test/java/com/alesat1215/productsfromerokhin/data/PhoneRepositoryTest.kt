package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhoneRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var limiter: UpdateLimiter
    @Mock
    private lateinit var phoneDao: PhoneDao
    private val phone = PhoneForOrder("phone")

    private lateinit var repository: PhoneRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(db.phoneDao()).thenReturn(phoneDao)
        `when`(db.phoneDao().phone()).thenReturn(MutableLiveData(phone))
        repository = PhoneRepository(remoteConfig, db, limiter)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun phone() {
        // Not update db (limiter)
        `when`(limiter.needUpdate()).thenReturn(false)
        var result: PhoneForOrder? = null
        repository.phone().observeForever { result = it }
        assertEquals(result, phone)
    }
}