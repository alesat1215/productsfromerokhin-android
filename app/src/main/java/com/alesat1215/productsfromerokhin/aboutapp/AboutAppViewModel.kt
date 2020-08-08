package com.alesat1215.productsfromerokhin.aboutapp

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IAboutAppRepository
import javax.inject.Inject

class AboutAppViewModel @Inject constructor(
    private val aboutAppRepository: IAboutAppRepository
) : ViewModel() {
}