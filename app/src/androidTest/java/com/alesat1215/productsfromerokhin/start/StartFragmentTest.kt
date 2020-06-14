package com.alesat1215.productsfromerokhin.start

import android.graphics.ColorSpace.match
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alesat1215.productsfromerokhin.R
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class StartFragmentTest {

    @Before
    fun setUp() {
        val scenario = launchFragmentInContainer<StartFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        sleep(30000)
        onView(withId(R.id.title)).check(matches(isDisplayed()))
    }
}