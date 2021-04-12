package dev.eastar.numberquiz.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.eastar.numberquiz.Main
import dev.eastar.numberquiz.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class MainFrTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<Main>()

    @Test
    fun onCreateView() {
        onView(withId(R.id.single))         // withId(R.id.my_view) is a ViewMatcher
            .perform(click())               // click() is a ViewAction
            .check(matches(isDisplayed()))  // matches(isDisplayed()) is a ViewAssertion
    }
}