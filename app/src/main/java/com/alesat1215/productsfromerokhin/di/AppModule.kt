package com.alesat1215.productsfromerokhin.di

import android.content.Context
import androidx.room.Room
import com.alesat1215.productsfromerokhin.data.AppDatabase
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun authFB() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun db(applicationContext: Context) =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "productsDatabase").build()

    /** Limiter for update database from remote config */
    @Provides
    fun limiter() = UpdateLimiter(1, TimeUnit.MINUTES)

    @Singleton
    @Provides
    fun remoteConfig() = FirebaseRemoteConfig.getInstance()

    @Singleton
    @Provides
    fun gson() = GsonBuilder().create()
}