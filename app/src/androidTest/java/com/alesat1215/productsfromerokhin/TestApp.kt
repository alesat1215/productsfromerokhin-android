package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.di.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TestApp : App() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerTestAppComponent.create()
}