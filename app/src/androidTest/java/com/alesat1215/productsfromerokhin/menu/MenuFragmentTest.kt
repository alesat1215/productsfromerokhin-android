package com.alesat1215.productsfromerokhin.menu

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.products123AndroidTest
import com.alesat1215.productsfromerokhin.products456AndroidTest
import com.alesat1215.productsfromerokhin.remoteDataMockAndroidTest
import org.hamcrest.core.IsNot.not
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class MenuFragmentTest {
    private val data by lazy { remoteDataMockAndroidTest() }

    @Before
    fun setUp() {
        launchFragmentInContainer<MenuFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.groups)).check(matches(isDisplayed()))
        onView(withId(R.id.products_menu)).check(matches(isDisplayed()))
    }

    @Test
    fun switchGroup() {
        val group = data.groups!!.first().name
        val productFromGroup = products123AndroidTest().first().name
        val group2 = data.groups!!.last().name
        val productFromGroup2 = products456AndroidTest().first().name

        onView(withText(productFromGroup)).check(matches(isDisplayed()))
        onView(withText(group2)).perform(click())
        onView(withText(productFromGroup2)).check(matches(isDisplayed()))
        onView(withText(productFromGroup)).check(doesNotExist())
        onView(withText(group)).perform(click())
        onView(withText(productFromGroup)).check(matches(isDisplayed()))
    }
}