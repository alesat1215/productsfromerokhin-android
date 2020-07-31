package com.alesat1215.productsfromerokhin.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep

@RunWith(MockitoJUnitRunner::class)
class TutorialRepositoryTest {
    @Mock
    private lateinit var remoteConfig: RemoteConfig
    @Mock
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    @Mock
    private lateinit var db: AppDatabase
    @Mock
    private lateinit var instructionsDao: InstructionsDao

    private val instructions = arrayOf(Instruction())

    @Mock
    private lateinit var limiter: RateLimiter
    @Mock
    private lateinit var gson: Gson

    private lateinit var repository: TutorialRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(db.instructionsDao()).thenReturn(instructionsDao)
        `when`(db.instructionsDao().instructions()).thenReturn(MutableLiveData(instructions.asList()))
        `when`(firebaseRemoteConfig.getString(TutorialRepository.INSTRUCTIONS)).thenReturn("")
        `when`(remoteConfig.firebaseRemoteConfig).thenReturn(firebaseRemoteConfig)
        `when`(gson.fromJson("", Array<Instruction>::class.java)).thenReturn(instructions)
        repository = TutorialRepository(remoteConfig, db, limiter, gson)
    }

    @Test
    fun instructions() {
        // Not update db (limiter)
        `when`(limiter.needUpdate()).thenReturn(false)
        var result: List<Instruction> = emptyList()
        repository.instructions().observeForever { result = it }
        assertEquals(result, instructions.toList())
        sleep(100)
        verify(db.instructionsDao(), never()).updateInstructions(instructions.asList())
        // Not update db (result onFailure)
        result = emptyList()
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.failure(Exception())))
        repository.instructions().observeForever { result = it }
        assertEquals(result, instructions.toList())
        sleep(100)
        verify(db.instructionsDao(), never()).updateInstructions(instructions.asList())
        // Update db
        result = emptyList()
        `when`(limiter.needUpdate()).thenReturn(true)
        `when`(remoteConfig.fetchAndActivate()).thenReturn(MutableLiveData(Result.success(Unit)))
        repository.instructions().observeForever { result = it }
        assertEquals(result, instructions.toList())
        sleep(100)
        verify(db.instructionsDao()).updateInstructions(instructions.asList())
    }
}