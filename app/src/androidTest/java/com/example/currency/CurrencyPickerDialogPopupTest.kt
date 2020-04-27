package com.example.currency

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.currency.fragment.CurrencyPickerDialogPopup
import com.example.currency.local.AppDatabase
import com.example.currency.network.ApiClient
import com.example.currency.repository.CurrencyRepositoryImpl
import com.example.currency.repository.ExchangeRateRepositoryImpl
import com.example.currency.view_model.CurrencyViewModel
import com.example.currency.view_model.ExchangeRateViewModel
import org.junit.Before
import org.junit.Test

class CurrencyPickerDialogPopupTest {
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
                if (className == CurrencyPickerDialogPopup::class.java.name) {
                    return CurrencyPickerDialogPopup(currencyViewModel, exchangeRateViewModel)
                }
                return super.instantiate(classLoader, className)
            }
        }
        launchFragmentInContainer<CurrencyPickerDialogPopup>(null, factory = factory)
    }

    @Test
    fun launchFragmentAndVerifyUI() {

        Espresso.onView(ViewMatchers.withId(R.id.tvHeader)).check(
            ViewAssertions.matches(
                ViewMatchers.isCompletelyDisplayed()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.rvCurrencies)).check(
            ViewAssertions.matches(
                ViewMatchers.isCompletelyDisplayed()
            )
        )
    }
}