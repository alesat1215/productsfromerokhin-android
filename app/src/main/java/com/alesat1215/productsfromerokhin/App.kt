package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/** For auto inject in activity, fragments */
class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(applicationContext)
}