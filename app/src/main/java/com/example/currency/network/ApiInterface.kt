package com.example.currency.network

import com.example.currency.network.response.ExchangeRateListResponse
import com.example.currency.network.response.SupportedCurrencyListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api Interface
 */
interface ApiInterface {

    @GET("live")
    fun getExchangeRateList(
        @Query("access_key") accessKey: String
    ): Observable<ExchangeRateListResponse>

    @GET("list")
    fun getSupportedCurrencyList(
        @Query("access_key") accessKey: String
    ): Observable<SupportedCurrencyListResponse>
}
