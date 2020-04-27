package com.example.currency

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.currency.adapter.CurrencyHolder
import com.example.currency.fragment.MoneyConversionFragment
import com.example.currency.local.AppDatabase
import com.example.currency.network.ApiClient
import com.example.currency.repository.CurrencyRepositoryImpl
import com.example.currency.repository.ExchangeRateRepositoryImpl
import com.example.currency.view_model.CurrencyViewModel
import com.example.currency.view_model.ExchangeRateViewModel
import org.junit.Before
import org.junit.Test

class MoneyConversionFragmentTest {

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        val apiService = ApiClient().getService()

        val currencyRepository = CurrencyRepositoryImpl(apiService, appDatabase.currencyDao())
        val exchangeRateRepository =
            ExchangeRateRepositoryImpl(apiService, appDatabase.exchangeRateDao())
        val currencyViewModel = CurrencyViewModel(currencyRepository)
        val exchangeRateViewModel = ExchangeRateViewModel(exchangeRateRepository)

        val factory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                if (className == MoneyConversionFragment::class.java.name) {
                    return MoneyConversionFragment(currencyViewModel, exchangeRateViewModel)
                }
                return super.instantiate(classLoader, className)
            }
        }
        launchFragmentInContainer<MoneyConversionFragment>(null, factory = factory)
    }

    @Test
    fun launchFragmentAndVerifyUI() {
        Espresso.onView(ViewMatchers.withId(R.id.etInput)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.tvCurrency)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun tapToSelectCurrency() {
        Espresso.onView(ViewMatchers.withId(R.id.tvCurrency)).perform(
            ViewActions.click()
        )

        //Popup currency is displayed
        Espresso.onView(ViewMatchers.withId(R.id.tvHeader)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun conversionWorks() {
        //Fill in the amount
        Espresso.onView(ViewMatchers.withId(R.id.etInput)).perform(ViewActions.typeText("10"))

        //Tap to select currency
        Espresso.onView(ViewMatchers.withId(R.id.tvCurrency)).perform(
            ViewActions.click()
        )

        //Can scroll to 20st
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

    }
}