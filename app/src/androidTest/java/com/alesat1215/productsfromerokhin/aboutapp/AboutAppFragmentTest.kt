package com.alesat1215.productsfromerokhin.aboutapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.aboutproducts.AboutProductsFragment
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AboutAppFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<AboutAppFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.appName)).check(matches(isDisplayed()))
        onView(withId(R.id.appVersion)).check(matches(isDisplayed()))
        onView(withId(R.id.privacy)).check(matches(isDisplayed()))
        onView(withId(R.id.update)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun privacy() {
    }

    @Test
    fun update() {
    }
}