package com.alesat1215.productsfromerokhin.di

import android.content.Context
import androidx.room.Room
import com.alesat1215.productsfromerokhin.data.ProductsDatabase
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module
object AppModule {

    @Singleton
    @Provides
    fun authFB() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun dbFB() = FirebaseDatabase.getInstance().reference

    @Singleton
    @Provides
    fun db(applicationContext: Context) =
        Room.databaseBuilder(applicationContext, ProductsDatabase::class.java, "productsDatabase").build()

    @Qualifier
    @Retention(RUNTIME)
    annotation class DBfb

    /** Limiter for remote database  */
    @DBfb
    @Provides
    fun limiter() = RateLimiter(1, TimeUnit.MINUTES)
}