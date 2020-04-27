package com.example.currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.currency.model.Currency
import com.example.currency.network.ApiInterface
import com.example.currency.network.response.SupportedCurrencyListResponse
import com.example.currency.repository.CurrencyDao
import com.example.currency.repository.CurrencyRepositoryImpl
import com.example.currency.view_model.CurrencyViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test

class CurrencyViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val apiInterface: ApiInterface = mockk()
    private var currencyDao: CurrencyDao = mockk()

    @Test
    fun viewModelGetCameraListAndEmitRightData() {
        val repository = CurrencyRepositoryImpl(apiInterface, currencyDao)
        val liveData = MutableLiveData<List<Currency>>()

        every {
            currencyDao.getCurrencyList()
        } returns liveData

        every {
            currencyDao.insertCurrencyList(any())
        } returns Unit

        val response: SupportedCurrencyListResponse = mockk()

        val viewModel = spyk(CurrencyViewModel(repository))

        every {
            response.currencies
        } returns HashMap<String, String>()

        every {
            apiInterface.getSupportedCurrencyList(any())
        } returns Observable.just(response)

        every {
            viewModel.getSchedulerIO()
        } returns Schedulers.io()

        every {
            viewModel.getMainScheduler()
        } returns Schedulers.io()

        viewModel.getRemoteSupportCurrencyList()

        val testObserver = repository.getRemoteSupportedCurrencyList().test()

        testObserver.assertValue(response.currencies)
        testObserver.dispose()
    }
}