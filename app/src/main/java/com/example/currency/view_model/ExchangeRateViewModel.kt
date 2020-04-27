package com.example.currency.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currency.model.Currency
import com.example.currency.model.ExchangeRate
import com.example.currency.repository.IExchangeRateRepository

class ExchangeRateViewModel(private val exchangeRepository: IExchangeRateRepository) :
    BaseViewModel(),
    IExchangeRateViewModel {

    private val selectedCurrency: MutableLiveData<Currency> = MutableLiveData()

    private val exchangeRate: LiveData<List<ExchangeRate>> =
        exchangeRepository.getLocalExchangeRateList()

    private val amount: MutableLiveData<Float> = MutableLiveData()

    fun getSelectedCurrency(): LiveData<Currency> {
        return selectedCurrency
    }

    fun updateSelectedCurrency(currency: Currency) {
        selectedCurrency.postValue(currency)
    }

    fun getExchangeRate(): LiveData<List<ExchangeRate>> {
        return exchangeRate
    }

    override fun requestRemoteExchangeRate() {
        compositeDisposable.add(
            exchangeRepository.getRemoteExchangeRateList()
                .subscribeOn(getSchedulerIO())
                .observeOn(getSchedulerIO())
                .subscribe({
                    exchangeRepository.updateLocalDB(it.timestamp * 1000, it.quotes)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getAmount(): LiveData<Float> {
        return amount
    }

    fun updateAmount(value: Float) {
        amount.value = value
    }
}

interface IExchangeRateViewModel {
    fun requestRemoteExchangeRate()
}