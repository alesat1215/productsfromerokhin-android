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
import com.alesat1215.productsfromerokhin.DataMock
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

    @Test
    fun onTabSelected() {
        val groupFirst = DataMock.groups.first().name
        val productFirst = DataMock.groups.first().products.first().name
        val groupLast = DataMock.groups.last().name
        val productLast = DataMock.groups.last().products.first().name
        // Scroll bottom
        onView(withText(productFirst)).check(matches(isDisplayed()))
        onView(withText(groupLast)).perform(click())
        onView(withText(productLast)).check(matches(isDisplayed()))
        onView(withText(productFirst)).check(doesNotExist())
        // Scroll start
        onView(withText(groupFirst)).perform(click())
        onView(withText(productFirst)).check(matches(isDisplayed()))
    }

    @Test
    fun onScroll() {
        val groupFirst = DataMock.groups.first().name
        val groupSecond = DataMock.groups[1].name
        val startPosition = 0
        val bottomPosition = DataMock.products.count() - 1
        // Scroll bottom
        onView(withText(groupFirst)).check(matches(isSelected()))
        onView(withId(R.id.products_menu)).perform(RecyclerViewActions.scrollToPosition<BindViewHolder>(bottomPosition))
        onView(withText(groupSecond)).check(matches(isSelected()))
        // Scroll start
        onView(withId(R.id.products_menu)).perform(RecyclerViewActions.scrollToPosition<BindViewHolder>(startPosition))
        onView(withText(groupFirst)).check(matches(isSelected()))
    }
}