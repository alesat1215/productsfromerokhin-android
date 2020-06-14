package com.alesat1215.productsfromerokhin.start

import android.graphics.ColorSpace.match
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.remoteDataMockAndroidTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

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
        onView(withId(R.id.title)).check(matches(withText(data.title)))
        onView(withId(R.id.imgTitle)).check(matches(withText(data.imgTitle)))
        onView(withId(R.id.productsTitle)).check(matches(withText(data.productsTitle)))
        onView(withId(R.id.productsTitle2)).check(matches(withText(data.productsTitle2)))
    }
}