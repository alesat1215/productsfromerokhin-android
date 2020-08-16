package com.alesat1215.productsfromerokhin.aboutapp

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.DataMock
import com.alesat1215.productsfromerokhin.MainActivity
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.aboutproducts.AboutProductsFragment
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AboutAppFragmentTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        launchFragmentInContainer<AboutAppFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.appName)).check(matches(isDisplayed()))
        onView(withId(R.id.appVersion)).check(matches(isDisplayed()))
        onView(withId(R.id.privacy)).check(matches(isDisplayed()))
//        onView(withId(R.id.update)).check(matches(isDisplayed()))
    }

    @Test
    fun privacy() {
        onView(withId(R.id.privacy)).perform(click())
        val chooser = Intents.getIntents().last()
        assertEquals(chooser.action, Intent.ACTION_CHOOSER)
        assertTrue(chooser.hasExtra(Intent.EXTRA_INTENT))
        val intent = chooser.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
        assertEquals(intent?.action, Intent.ACTION_VIEW)
        assertEquals(intent?.data, Uri.parse(DataMock.aboutApp.privacy))
    }

//    @Test
//    fun update() {
//        onView(withId(R.id.update)).perform(click())
//        val chooser = Intents.getIntents().last()
//        assertEquals(chooser.action, Intent.ACTION_CHOOSER)
//        assertTrue(chooser.hasExtra(Intent.EXTRA_INTENT))
//        val intent = chooser.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
//        assertEquals(intent?.action, Intent.ACTION_VIEW)
//        assertEquals(intent?.data, Uri.parse(DataMock.aboutApp.googlePlay + intentsTestRule.activity.packageName))
//    }
}