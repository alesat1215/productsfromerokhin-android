package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alesat1215.productsfromerokhin.data.local.ProductsDatabase
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfigRepository
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface ITutorialRepository {
    /** @return instructions for order */
    fun instructions(): LiveData<List<Instruction>>
}
@Singleton
class TutorialRepository @Inject constructor(
    /** Firebase remote config */
    private val remoteConfigRepository: RemoteConfigRepository,
    /** Room database */
    private val db: ProductsDatabase,
    /** Limiting the frequency of update database */
    private val limiter: RateLimiter,
    /** For parse JSON from remote config */
    private val gson: Gson
) : ITutorialRepository {
    /** Get instructions from Room only once */
    private val instructions by lazy { db.instructionsDao().instructions() }

    override fun instructions(): LiveData<List<Instruction>> {
        updateDB()
        return instructions
    }
    /** Update Room from remote config if needed */
    private fun updateDB() {
        // Return if limit is over
        if (!limiter.shouldFetch()) return
        // Fetch data from remote config
//        fetchAndActivate {
//            // Update data in Room
//            GlobalScope.launch(Dispatchers.IO) {
//                // Get instructions from JSON
//                val remoteInstructions = gson.fromJson(remoteConfig.getString(RemoteConfigRepository.INSTRUCTIONS), Array<Instruction>::class.java).asList()
//                Logger.d("Fetch instructions from remote config: ${remoteInstructions.count()}")
//                // Update Room
//                db.instructionsDao().updateInstructions(remoteInstructions)
//            }
//        }
        val observer = Observer<Result<Boolean>> {
            it.onSuccess { updateInstructions() }
            it.onFailure { Logger.d("Instructions is not update") }
        }
        remoteConfigRepository.fetchAndActivate().removeObserver(observer)
        remoteConfigRepository.fetchAndActivate().observeForever(observer)
    }

    private fun updateInstructions() {
        // Update data in Room
        GlobalScope.launch(Dispatchers.IO) {
            // Get instructions from JSON
            val remoteInstructions = gson.fromJson(remoteConfigRepository.remoteConfig.getString(RemoteConfigRepository.INSTRUCTIONS), Array<Instruction>::class.java).asList()
            Logger.d("Fetch instructions from remote config: ${remoteInstructions.count()}")
            // Update Room
            db.instructionsDao().updateInstructions(remoteInstructions)
        }
    }
}