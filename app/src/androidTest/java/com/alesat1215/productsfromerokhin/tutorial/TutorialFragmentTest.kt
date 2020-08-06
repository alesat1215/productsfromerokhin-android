package com.alesat1215.productsfromerokhin.tutorial

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.DataMock
import com.alesat1215.productsfromerokhin.R
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TutorialFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<TutorialFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        DataMock.instructions.forEach {
            onView(withText(it.title)).check(matches(isDisplayed()))
            onView(withText(it.text)).check(matches(isDisplayed()))
            onView(withId(R.id.pager_tutorial)).perform(swipeLeft())
        }
    }

    @After
    fun tearDown() {
    }
}