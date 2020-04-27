package com.example.currency.view_model

import androidx.lifecycle.LiveData
import com.example.currency.model.Currency
import com.example.currency.repository.ICurrencyRepository

class CurrencyViewModel(private val currencyRepository: ICurrencyRepository) :
    BaseViewModel(), ICurrencyViewModel {

    private val currencyList: LiveData<List<Currency>> =
        currencyRepository.getLocalSupportedCurrencyList()

    override fun getRemoteSupportCurrencyList() {
        compositeDisposable.add((currencyRepository.getRemoteSupportedCurrencyList()
            .subscribeOn(getSchedulerIO())
            .observeOn(getSchedulerIO())
            .subscribe({
                currencyRepository.updateLocalDB(System.currentTimeMillis(), it)
            }, {
                it.printStackTrace()
            }))
        )
    }

    fun getCurrencyList(): LiveData<List<Currency>> {
        return currencyList
    }
}

interface ICurrencyViewModel {
    fun getRemoteSupportCurrencyList()
}