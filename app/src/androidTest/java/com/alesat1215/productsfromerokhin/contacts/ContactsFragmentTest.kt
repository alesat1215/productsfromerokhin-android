package com.alesat1215.productsfromerokhin.contacts

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alesat1215.productsfromerokhin.DataMock
import com.alesat1215.productsfromerokhin.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class ContactsFragmentTest {

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
}