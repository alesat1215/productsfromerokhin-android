package com.alesat1215.productsfromerokhin.profile

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.alesat1215.productsfromerokhin.R
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ProfileFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<ProfileFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(ViewMatchers.withId(R.id.profile_name))
            .check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.profile_phone))
            .check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.profile_address))
            .check(matches(isDisplayed()))
    }

    @Test
    fun save() {
    }
}