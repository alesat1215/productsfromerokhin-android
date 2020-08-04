package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface ITutorialRepository {
    /** @return instructions for order & update Room from remote config if needed */
    fun instructions(): LiveData<List<Instruction>>
}
/** Repository for instructions.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
@Singleton
class TutorialRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : ITutorialRepository {
    /** @return LiveData with instructions from Room only once */
    private val instructions by lazy { db.instructionsDao().instructions() }

    override fun instructions(): LiveData<List<Instruction>> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateInstructions)) { instructions }
    }

    /** Update data in Room in background */
    private suspend fun updateInstructions() = withContext(Dispatchers.Default) {
        // Get instructions from remote config
        val remoteInstructions = gson.fromJson(dbUpdater.firebaseRemoteConfig.getString(INSTRUCTIONS), Array<Instruction>::class.java).asList()
        Logger.d("Fetch instructions from remote config: ${remoteInstructions.count()}")
        // Update Room
        db.instructionsDao().updateInstructions(remoteInstructions)
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val INSTRUCTIONS = "instructions"
    }

}