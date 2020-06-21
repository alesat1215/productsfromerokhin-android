package com.alesat1215.productsfromerokhin.menu

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.products456AndroidTest
import com.alesat1215.productsfromerokhin.remoteDataMockAndroidTest
import com.alesat1215.productsfromerokhin.util.BindViewHolder
import org.hamcrest.CoreMatchers.not
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
//        onView(withId(R.id.products_menu))
//            .perform(RecyclerViewActions.scrollToPosition<BindViewHolder>(data.productsWithGroupId()!!.count() - 1))
//        onView(withId(R.id.groups)).check()
//        sleep(10000)
//        onView(withText(products456AndroidTest().first().name)).check(matches(not(isDisplayed())))
        sleep(10000)
        onView(withText(data.groups!!.last().name)).perform(click())
        sleep(10000)
        onView(withText(products456AndroidTest().first().name)).check(matches(isDisplayed()))
    }
}