package com.alesat1215.productsfromerokhin.start

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.products123AndroidTest
import com.alesat1215.productsfromerokhin.products456AndroidTest
import com.alesat1215.productsfromerokhin.remoteDataMockAndroidTest
import com.alesat1215.productsfromerokhin.util.BindViewHolder
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class StartFragmentTest {
    private val data by lazy { remoteDataMockAndroidTest() }

    @Before
    fun setUp() {
        launchFragmentInContainer<StartFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        // Titles
        onView(withId(R.id.title)).check(matches(withText(data.title)))
        onView(withId(R.id.imgTitle)).check(matches(withText(data.imgTitle)))
        onView(withId(R.id.productsTitle)).check(matches(withText(data.productsTitle)))
        onView(withId(R.id.productsTitle2)).check(matches(withText(data.productsTitle2)))
        // Products
        val products = products123AndroidTest()
        onView(withText(products.first().name)).check(matches(isDisplayed()))
        onView(withId(R.id.products)).perform(scrollToPosition<BindViewHolder>(1))
        onView(withText(products[1].name)).check(matches(isDisplayed()))
        onView(withId(R.id.products)).perform(scrollToPosition<BindViewHolder>(products.count() - 1))
        onView(withText(products.last().name)).check(matches(isDisplayed()))
        // Products2
        val products2 = products456AndroidTest()
        onView(withText(products2.first().name)).check(matches(isDisplayed()))
        onView(withId(R.id.products2)).perform(scrollToPosition<BindViewHolder>(1))
        onView(withText(products2[1].name)).check(matches(isDisplayed()))
        onView(withId(R.id.products2)).perform(scrollToPosition<BindViewHolder>(products2.count() - 1))
        onView(withText(products2.last().name)).check(matches(isDisplayed()))
    }
}