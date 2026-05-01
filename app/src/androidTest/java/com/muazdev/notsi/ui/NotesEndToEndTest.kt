package com.muazdev.notsi.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import com.muazdev.notsi.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class NotesEndToEndTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addNote_appearsInList() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.fabAdd)).perform(click())

        onView(withId(R.id.etTitle)).perform(replaceText("End to End Title"))
        onView(withId(R.id.etDesc)).perform(replaceText("End to End Description"))

        onView(withId(R.id.ivDone)).perform(click())

        onView(withText("End to End Title")).check(matches(isDisplayed()))
        onView(withText("End to End Description")).check(matches(isDisplayed()))
    }
}
