package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.di.DaggerAppComponent
import com.orhanobut.logger.AndroidLogAdapter
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import com.orhanobut.logger.Logger

/** For auto inject in activity, fragments */
open class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        // Create injector
        DaggerAppComponent.factory().create(applicationContext).also {
            // Setup Logger
            Logger.addLogAdapter(AndroidLogAdapter())
        }
}