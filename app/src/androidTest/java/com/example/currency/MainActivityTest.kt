package com.example.currency

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.currency.activity.MainActivity
import com.example.currency.adapter.CurrencyHolder
import com.example.currency.fragment.MoneyConversionFragment
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun makeSureMainFragmentIsVisibleAndGetData() {
        val activity: MainActivity = activityRule.activity

        val fragment = activity.supportFragmentManager.findFragmentById(R.id.mainFrame)
        Assert.assertEquals(true, fragment != null)
        Assert.assertEquals(true, fragment is MoneyConversionFragment)
    }

    @Test
    fun checkConversionWorks() {
        Thread.sleep(2000)
        //Fill in the amount
        Espresso.onView(ViewMatchers.withId(R.id.etInput)).perform(ViewActions.typeText("10"))

        //Tap to select currency
        Espresso.onView(ViewMatchers.withId(R.id.tvCurrency)).perform(
            ViewActions.click()
        )

        //Can scroll and select 20st currency
        Espresso.onView(ViewMatchers.withId(R.id.rvCurrencies))
            .perform(RecyclerViewActions.scrollToPosition<CurrencyHolder>(20))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            ).perform(
                RecyclerViewActions.actionOnItemAtPosition<CurrencyHolder>(
                    20,
                    ViewActions.click()
                )
            )

        //Check can scroll to item 20
        Espresso.onView(ViewMatchers.withId(R.id.rvExchangeRate))
            .perform(RecyclerViewActions.scrollToPosition<CurrencyHolder>(20))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )
    }
}