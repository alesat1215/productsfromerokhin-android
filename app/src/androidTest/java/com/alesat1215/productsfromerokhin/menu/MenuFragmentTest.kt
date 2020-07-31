package com.alesat1215.productsfromerokhin.menu

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.util.BindViewHolder
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MenuFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<MenuFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.groups_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.products_menu)).check(matches(isDisplayed()))
    }

//    @Test
//    fun onTabSelected() {
//        val group = RemoteDataMockAndroidTest.data.groups!!.first().name
//        val productFromGroup = RemoteDataMockAndroidTest.products123AndroidTest().first().name
//        val group2 = RemoteDataMockAndroidTest.data.groups!!.last().name
//        val productFromGroup2 = RemoteDataMockAndroidTest.products456AndroidTest().first().name
//        // Scroll bottom
//        onView(withText(productFromGroup)).check(matches(isDisplayed()))
//        onView(withText(group2)).perform(click())
//        onView(withText(productFromGroup2)).check(matches(isDisplayed()))
//        onView(withText(productFromGroup)).check(doesNotExist())
//        // Scroll start
//        onView(withText(group)).perform(click())
//        onView(withText(productFromGroup)).check(matches(isDisplayed()))
//    }

//    @Test
//    fun onScroll() {
//        val group = RemoteDataMockAndroidTest.data.groups().first().name
//        val group2 = RemoteDataMockAndroidTest.data.groups().last().name
//        val startPosition = 0
//        val bottomPosition = RemoteDataMockAndroidTest.data.products().count() - 1
//        // Scroll bottom
//        onView(withText(group)).check(matches(isSelected()))
//        onView(withId(R.id.products_menu)).perform(RecyclerViewActions.scrollToPosition<BindViewHolder>(bottomPosition))
//        onView(withText(group2)).check(matches(isSelected()))
//        // Scroll start
//        onView(withId(R.id.products_menu)).perform(RecyclerViewActions.scrollToPosition<BindViewHolder>(startPosition))
//        onView(withText(group)).check(matches(isSelected()))
//    }
}