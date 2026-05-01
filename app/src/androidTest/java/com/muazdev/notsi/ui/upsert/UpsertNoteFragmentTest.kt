package com.muazdev.notsi.ui.upsert

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.muazdev.notsi.R
import com.muazdev.notsi.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
class UpsertNoteFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun saveNote_navigatesUp() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<UpsertNoteFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.etTitle)).perform(replaceText("New Note"))
        onView(withId(R.id.etDesc)).perform(replaceText("New Description"))
        onView(withId(R.id.ivDone)).perform(click())

        verify(navController).navigateUp()
    }
}
