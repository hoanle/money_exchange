package com.example.currency

import com.example.currency.network.ApiInterface
import com.example.currency.network.response.ExchangeRateListResponse
import com.example.currency.repository.ExchangeRateDao
import com.example.currency.repository.ExchangeRateRepositoryImpl
import com.example.currency.util.Constant
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test

class ExchangeRateRepositoryTest {

    private val apiInterface: ApiInterface = mockk()
    private val exchangeRateDao: ExchangeRateDao = mockk()

    private val repository = ExchangeRateRepositoryImpl(apiInterface, exchangeRateDao)

    @Test
    fun repositoryEmitsRightData() {
        val response: ExchangeRateListResponse = mockk()

        every {
            apiInterface.getExchangeRateList(Constant.API_KEY)
        } returns Observable.just(response)

        val testObserver = repository.getRemoteExchangeRateList().test()
        testObserver.assertValue(response)
        testObserver.dispose()
    }
}