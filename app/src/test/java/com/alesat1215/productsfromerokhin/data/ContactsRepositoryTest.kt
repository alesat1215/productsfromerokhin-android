package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContactsRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var limiter: UpdateLimiter

    private lateinit var dbUpdater: DatabaseUpdater
    @Mock
    private lateinit var contactsDao: ContactsDao
    @Mock
    private lateinit var gson: Gson
    private val contacts = Contacts()

    private lateinit var repository: ContactsRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(db.contactsDao()).thenReturn(contactsDao)
        `when`(db.contactsDao().contacts()).thenReturn(MutableLiveData(contacts))
        `when`(firebaseRemoteConfig.getString(ContactsRepository.CONTACTS)).thenReturn("")
        `when`(gson.fromJson("", Contacts::class.java)).thenReturn(contacts)
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        dbUpdater = DatabaseUpdater(limiter, remoteConfig)
        repository = ContactsRepository(db, dbUpdater, gson)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun contacts() = runBlocking {
        // Not update db
        var result: Contacts? = null
        `when`(limiter.needUpdate()).thenReturn(false)
        repository.contacts().observeForever { result = it }
        sleep(100)
        assertEquals(result, contacts)
        verify(contactsDao, never()).updateContacts(contacts)
        // Update db
        result = null
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.contacts().observeForever { result = it }
        sleep(100)
        assertEquals(result, contacts)
        verify(contactsDao).updateContacts(contacts)
    }
}