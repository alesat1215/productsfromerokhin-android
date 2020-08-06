package com.alesat1215.productsfromerokhin.aboutproducts

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.DataMock
import com.alesat1215.productsfromerokhin.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AboutProductsFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<AboutProductsFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.about_products_list)).check(matches(isDisplayed()))
        onView(withId(R.id.about_products_list2)).check(matches(isDisplayed()))
        DataMock.aboutProducts.forEach {
            onView(withText(it.text)).check(matches(isDisplayed()))
        }
    }

    @After
    fun tearDown() {
    }
}