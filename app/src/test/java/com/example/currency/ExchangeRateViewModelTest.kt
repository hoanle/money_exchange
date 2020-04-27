package com.example.currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.currency.model.Currency
import com.example.currency.model.ExchangeRate
import com.example.currency.network.ApiInterface
import com.example.currency.network.response.ExchangeRateListResponse
import com.example.currency.network.response.SupportedCurrencyListResponse
import com.example.currency.repository.CurrencyDao
import com.example.currency.repository.CurrencyRepositoryImpl
import com.example.currency.repository.ExchangeRateDao
import com.example.currency.repository.ExchangeRateRepositoryImpl
import com.example.currency.view_model.CurrencyViewModel
import com.example.currency.view_model.ExchangeRateViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test

class ExchangeRateViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val apiInterface: ApiInterface = mockk()
    private var exchangeRateDao: ExchangeRateDao = mockk()

    @Test
    fun viewModelGetCameraListAndEmitRightData() {
        val repository = ExchangeRateRepositoryImpl(apiInterface, exchangeRateDao)
        val liveData = MutableLiveData<List<ExchangeRate>>()

        every {
            exchangeRateDao.getExchangeRateList()
        } returns liveData

        every {
            exchangeRateDao.insertExchangeRateList(any())
        } returns Unit

        val response: ExchangeRateListResponse = mockk()

        every {
            response.timestamp
        } returns System.currentTimeMillis()

        val viewModel = spyk(ExchangeRateViewModel(repository))

        every {
            response.quotes
        } returns HashMap()

        every {
            apiInterface.getExchangeRateList(any())
        } returns Observable.just(response)

        every {
            viewModel.getSchedulerIO()
        } returns Schedulers.io()

        every {
            viewModel.getMainScheduler()
        } returns Schedulers.io()

        viewModel.requestRemoteExchangeRate()

        val testObserver = repository.getRemoteExchangeRateList().test()

        testObserver.assertValue(response)
        testObserver.dispose()
    }
}