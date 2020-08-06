package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.DataMock
import javax.inject.Inject
import javax.inject.Singleton

/** For UI testing with dagger */
@Singleton
class TutorialRepositoryMock @Inject constructor() : ITutorialRepository {
    override fun instructions() = MutableLiveData(DataMock.instructions)
}