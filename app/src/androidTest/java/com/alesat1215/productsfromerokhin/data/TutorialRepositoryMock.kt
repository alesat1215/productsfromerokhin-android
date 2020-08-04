package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

/** For UI testing with dagger */
@Singleton
class TutorialRepositoryMock @Inject constructor() : ITutorialRepository {
    override fun instructions(): LiveData<List<Instruction>> {
        TODO("Not yet implemented")
    }
}