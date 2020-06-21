package com.alesat1215.productsfromerokhin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkViews() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun navigation() {
        onView(withId(R.id.startFragment)).check(matches(isDisplayed()))
        // Navigate to menu
        onView(withContentDescription(R.string.menu)).perform(click())
        onView(withId(R.id.menuFragment)).check(matches(isDisplayed()))
        // Navigate to start
        onView(withContentDescription(R.string.start)).perform(click())
        onView(withId(R.id.startFragment)).check(matches(isDisplayed()))

    }
}