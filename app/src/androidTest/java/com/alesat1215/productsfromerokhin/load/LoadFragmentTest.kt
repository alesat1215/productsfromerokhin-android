package com.alesat1215.productsfromerokhin.load

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alesat1215.productsfromerokhin.R
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoadFragmentTest {
    private lateinit var navController: NavController

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)
        launchFragmentInContainer(themeResId = R.style.AppTheme) {
            LoadFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever {
                    if (it != null) Navigation.setViewNavController(fragment.requireView(), navController)
                }
            }
        }
    }

    @Test
    fun checkViews() {
        onView(withId(R.id.loading)).check(matches(isDisplayed()))
        assertEquals(navController.currentDestination?.id, R.id.startFragment)
    }
}