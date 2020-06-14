package com.alesat1215.productsfromerokhin

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/** For UI testing with TestApp & TestAppComponent */
class DaggerAndroidJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}