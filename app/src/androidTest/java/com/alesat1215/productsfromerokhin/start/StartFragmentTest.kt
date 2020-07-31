package com.alesat1215.productsfromerokhin.start

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class StartFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<StartFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        sleep(10000)
        onView(withId(R.id.title_start)).check(matches(withText("")))
    }

//    @Test
//    fun checkViews() {
//        // Titles
//        onView(withId(R.id.title_start)).check(matches(withText(RemoteDataMockAndroidTest.data.title)))
//        onView(withId(R.id.imgTitle_start)).check(matches(withText(RemoteDataMockAndroidTest.data.imgTitle)))
//        onView(withId(R.id.productsTitle_start)).check(matches(withText(RemoteDataMockAndroidTest.data.productsTitle)))
//        onView(withId(R.id.productsTitle2_start)).check(matches(withText(RemoteDataMockAndroidTest.data.productsTitle2)))
//        // Products
//        val products = RemoteDataMockAndroidTest.products123AndroidTest()
//        onView(withText(products.first().name)).check(matches(isDisplayed()))
//        onView(withId(R.id.products_start)).perform(scrollToPosition<BindViewHolder>(1))
//        onView(withText(products[1].name)).check(matches(isDisplayed()))
//        onView(withId(R.id.products_start)).perform(scrollToPosition<BindViewHolder>(products.count() - 1))
//        onView(withText(products.last().name)).check(matches(isDisplayed()))
//        // Products2
//        val products2 = RemoteDataMockAndroidTest.products456AndroidTest()
//        onView(withText(products2.first().name)).check(matches(isDisplayed()))
//        onView(withId(R.id.products2_start)).perform(scrollToPosition<BindViewHolder>(1))
//        onView(withText(products2[1].name)).check(matches(isDisplayed()))
//        onView(withId(R.id.products2_start)).perform(scrollToPosition<BindViewHolder>(products2.count() - 1))
//        onView(withText(products2.last().name)).check(matches(isDisplayed()))
//    }
}