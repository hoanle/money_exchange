package com.example.currency

import com.example.currency.network.ApiInterface
import com.example.currency.network.response.SupportedCurrencyListResponse
import com.example.currency.repository.CurrencyDao
import com.example.currency.repository.CurrencyRepositoryImpl
import com.example.currency.util.Constant
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test

class CurrencyRepositoryTest {

    private val apiInterface: ApiInterface = mockk()
    private var currencyDao: CurrencyDao = mockk()

    @Test
    fun repositoryEmitsRightRemoteData() {
        val repository = CurrencyRepositoryImpl(apiInterface, currencyDao)

        val response: SupportedCurrencyListResponse = mockk()
        every {
            response.currencies
        } returns HashMap<String, String>()

        every {
            apiInterface.getSupportedCurrencyList(Constant.API_KEY)
        } returns Observable.just(response)

        val testObserver = repository.getRemoteSupportedCurrencyList().test()
        testObserver.assertValue(response.currencies)
        testObserver.dispose()
    }
}