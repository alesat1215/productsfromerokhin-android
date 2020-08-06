package com.alesat1215.productsfromerokhin.contacts

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alesat1215.productsfromerokhin.DataMock
import com.alesat1215.productsfromerokhin.MainActivity
import com.alesat1215.productsfromerokhin.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ContactsFragmentTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        launchFragmentInContainer<ContactsFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.contacts_phone_title)).check(matches(isDisplayed()))
        onView(withText(R.string.our_phone)).check(matches(isDisplayed()))
        onView(withId(R.id.contacts_phone_text)).check(matches(isDisplayed()))
        onView(withText(DataMock.contacts.phone)).check(matches(isDisplayed()))
        onView(withId(R.id.contacts_address_title)).check(matches(isDisplayed()))
        onView(withId(R.id.contacts_address_text)).check(matches(isDisplayed()))
        onView(withText(R.string.our_address)).check(matches(isDisplayed()))
        onView(withText(DataMock.contacts.address)).check(matches(isDisplayed()))
        onView(withId(R.id.contacts_call)).check(matches(isDisplayed()))
    }

    @Test
    fun call() {
        onView(withId(R.id.contacts_call)).perform(click())
        val chooser = Intents.getIntents().last()
        assertEquals(chooser.action, Intent.ACTION_CHOOSER)
        assertTrue(chooser.hasExtra(Intent.EXTRA_INTENT))
        val call = chooser.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
        assertEquals(call?.action, Intent.ACTION_DIAL)
        assertEquals(call?.data, Uri.parse("tel:${DataMock.contacts.phone}"))
    }
}