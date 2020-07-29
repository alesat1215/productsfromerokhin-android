package com.alesat1215.productsfromerokhin.tutorial

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.ITutorialRepository
import javax.inject.Inject

class TutorialViewModel @Inject constructor(
    private val repository: ITutorialRepository
) : ViewModel() {
    fun instructions() = repository.instructions()
}