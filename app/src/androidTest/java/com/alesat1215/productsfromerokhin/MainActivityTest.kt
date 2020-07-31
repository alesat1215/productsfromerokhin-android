package com.alesat1215.productsfromerokhin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
        // Navigate to cart
        onView(withContentDescription(R.string.cart)).perform(click())
        onView(withId(R.id.cartFragment)).check(matches(isDisplayed()))
        // Navigate to profile
        onView(withContentDescription(R.string.profile)).perform(click())
        onView(withId(R.id.profileFragment)).check(matches(isDisplayed()))
        // Navigate to more
        onView(withContentDescription(R.string.more)).perform(click())
        onView(withId(R.id.moreFragment)).check(matches(isDisplayed()))
    }
}