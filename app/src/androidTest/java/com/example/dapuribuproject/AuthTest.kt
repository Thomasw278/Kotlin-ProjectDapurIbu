package com.example.dapuribuproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dapuribuproject.loginregis.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginEmptyFields() {
        onView(withId(R.id.btnSimpan)).perform(click())
        onView(withId(R.id.etUsername)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToRegisterAndBack() {
        onView(withId(R.id.btnKeRegistrasi)).perform(click())
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.btnKeLogin)).perform(click())
        onView(withId(R.id.etUsername)).check(matches(isDisplayed()))
    }

    @Test
    fun testRegistrationFlow() {
        onView(withId(R.id.btnKeRegistrasi)).perform(click())
        onView(withId(R.id.etUsername)).perform(typeText("Andi"), closeSoftKeyboard())
        onView(withId(R.id.etEmail)).perform(typeText("andi@example.com"), closeSoftKeyboard())
        onView(withId(R.id.ettglLahir)).perform(typeText("12-12-1995"), closeSoftKeyboard())
        onView(withId(R.id.etRole)).perform(typeText("user"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.btnSimpan)).perform(click())

        onView(withId(R.id.etUsername)).check(matches(withText("Andi")))
        onView(withId(R.id.etPassword)).check(matches(withText("password123")))
    }

    @Test
    fun testLoginFlowSuccess() {
        onView(withId(R.id.etUsername)).perform(clearText(), typeText("wisnu"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(clearText(), typeText("wisnu123"), closeSoftKeyboard())

        onView(withId(R.id.btnSimpan)).perform(click())

        onView(withId(R.id.containerPopuler)).check(matches(isDisplayed()))
    }
}
