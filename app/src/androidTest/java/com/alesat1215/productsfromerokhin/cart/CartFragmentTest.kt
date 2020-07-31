package com.alesat1215.productsfromerokhin.cart

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CartFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<CartFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.products_cart)).check(matches(isDisplayed()))
        onView(withId(R.id.result_text)).check(matches(isDisplayed()))
        onView(withId(R.id.result_sum)).check(matches(isDisplayed()))
        onView(withId(R.id.send_cart)).check(matches(isDisplayed()))
    }
}