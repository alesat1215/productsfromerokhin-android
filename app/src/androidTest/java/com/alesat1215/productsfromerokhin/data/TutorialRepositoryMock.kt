package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class TutorialRepositoryMock @Inject constructor() : ITutorialRepository {
    override fun instructions(): LiveData<List<Instruction>> {
        TODO("Not yet implemented")
    }
}