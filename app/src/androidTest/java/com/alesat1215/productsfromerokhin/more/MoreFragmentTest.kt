package com.alesat1215.productsfromerokhin.more

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MoreFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<MoreFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.tutorial_more)).check(matches(isDisplayed()))
        onView(withId(R.id.about_products_more)).check(matches(isDisplayed()))
        onView(withId(R.id.contacts_more)).check(matches(isDisplayed()))
        onView(withId(R.id.share_more)).check(matches(isDisplayed()))
        onView(withId(R.id.about_app_more)).check(matches(isDisplayed()))
    }

}