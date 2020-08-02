package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfig
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
class TutorialRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var dbUpdater: DatabaseUpdater
    @Mock
    private lateinit var instructionsDao: InstructionsDao

    private val instructions = arrayOf(Instruction())

    @Mock
    private lateinit var limiter: UpdateLimiter
    @Mock
    private lateinit var gson: Gson

    private lateinit var repository: TutorialRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(db.instructionsDao()).thenReturn(instructionsDao)
        `when`(db.instructionsDao().instructions()).thenReturn(MutableLiveData(instructions.asList()))
        `when`(firebaseRemoteConfig.getString(TutorialRepository.INSTRUCTIONS)).thenReturn("")
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        `when`(gson.fromJson("", Array<Instruction>::class.java)).thenReturn(instructions)
        repository = TutorialRepository(db, dbUpdater, gson)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun instructions() = runBlocking {
        // Not update db (limiter)
        `when`(limiter.needUpdate()).thenReturn(false)
        var result: List<Instruction> = emptyList()
        repository.instructions().observeForever { result = it }
        sleep(100)
        assertEquals(result, instructions.toList())
        sleep(100)
        verify(db.instructionsDao(), never()).updateInstructions(instructions.asList())
        // Not update db (result onFailure)
        result = emptyList()
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
        repository.instructions().observeForever { result = it }
        sleep(100)
        assertEquals(result, instructions.toList())
        sleep(100)
        verify(db.instructionsDao(), never()).updateInstructions(instructions.asList())
        // Update db
        result = emptyList()
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.instructions().observeForever { result = it }
        sleep(100)
        assertEquals(result, instructions.toList())
        sleep(100)
        verify(db.instructionsDao()).updateInstructions(instructions.asList())
    }
}