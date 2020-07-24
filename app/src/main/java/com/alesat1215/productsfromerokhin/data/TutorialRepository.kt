package com.alesat1215.productsfromerokhin.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.alesat1215.productsfromerokhin.data.local.ProductsDatabase
import com.alesat1215.productsfromerokhin.di.AppModule.DBfb
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfigRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ITutorialRepository : RemoteConfigRepository {
    /** @return instructions for order */
    fun instructions(): LiveData<List<Instruction>>
}

class TutorialRepository @Inject constructor(
    /** Firebase remote config */
    override val remoteConfig: FirebaseRemoteConfig,
    /** Room database */
    private val db: ProductsDatabase,
    /** Limiting the frequency of update database */
    @DBfb private val dbUpdateLimiter: RateLimiter,
    /** For parse JSON from remote config */
    private val gson: Gson
) : ITutorialRepository {
    /** Key for instructions in remote config */
    private val INSTRUCTIONS_KEY = "instructions"
    /** Get instructions from Room only once */
    private val instructions by lazy { db.productsDao().instructions() }

    override fun instructions(): LiveData<List<Instruction>> {
        updateDB()
        return instructions
    }
    /** Update Room from remote config if needed */
    private fun updateDB() {
        // Return if limit is over
        if (!dbUpdateLimiter.shouldFetch()) return
        // Fetch data from remote config
        fetchAndActivate {
            // Update data in Room
            GlobalScope.launch(Dispatchers.IO) {
                // Get instructions from JSON
                val remoteInstructions = gson.fromJson(remoteConfig.getString(INSTRUCTIONS_KEY), Array<Instruction>::class.java).asList()
                Log.d("Tutorial", "Fetch instructions from remote config: ${remoteInstructions.count()}")
                // Update Room
                db.productsDao().updateInstructions(remoteInstructions)
            }
        }
    }
}