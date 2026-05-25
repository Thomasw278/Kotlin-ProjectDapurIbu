package com.example.dapuribuproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dapuribuproject.loginregis.LoginActivity
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeatureTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testSearchCatalog() {
        onView(withId(R.id.etUsername)).perform(typeText("wisnu"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("wisnu123"), closeSoftKeyboard())
        onView(withId(R.id.btnSimpan)).perform(click())

        onView(withId(R.id.etSearch)).perform(typeText("Beef"), pressImeActionButton())

        onView(withId(R.id.containerPopuler)).check(matches(isDisplayed()))
    }

    @Test
    fun testRoleAccessAsUser() {
        onView(withId(R.id.etUsername)).perform(typeText("wisnu"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("wisnu123"), closeSoftKeyboard())
        onView(withId(R.id.btnSimpan)).perform(click())

        onView(withText(containsString("Menu Populer"))).check(matches(isDisplayed()))

        onView(withId(R.id.tvTotalUser)).check(doesNotExist())
    }

    @Test
    fun testRoleAccessAsAdmin() {
        onView(withId(R.id.etUsername)).perform(typeText("admin"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("admin123"), closeSoftKeyboard())
        onView(withId(R.id.btnSimpan)).perform(click())

        onView(withText(containsString("Dashboard Admin"))).check(matches(isDisplayed()))

        onView(withId(R.id.tvTotalUser)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTotalResep)).check(matches(isDisplayed()))
    }
}
