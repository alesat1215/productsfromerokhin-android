package com.alesat1215.productsfromerokhin.start

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.DataMock
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
        // Titles
        onView(withId(R.id.title_start)).check(matches(withText(DataMock.titles.title)))
        onView(withId(R.id.imgTitle_start)).check(matches(withText(DataMock.titles.imgTitle)))
        onView(withId(R.id.productsTitle_start)).check(matches(withText(DataMock.titles.productsTitle)))
        onView(withId(R.id.productsTitle2_start)).check(matches(withText(DataMock.titles.productsTitle2)))
        // Products
        DataMock.products.filter { it.product?.inStart == true && it.product?.inStart2 == true }.forEach {
            onView(withText(it.product?.name)).check(matches(isDisplayed()))
            onView(withText(it.inCart.toString())).check(matches(isDisplayed()))
        }
    }

}