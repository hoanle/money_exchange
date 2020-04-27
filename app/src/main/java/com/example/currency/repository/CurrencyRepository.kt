package com.example.currency.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.currency.local.AppDatabase
import com.example.currency.model.Currency
import com.example.currency.network.ApiInterface
import com.example.currency.util.Constant
import com.example.currency.view_model.ThreadProvider
import io.reactivex.Observable

/**
 * Concrete class of Currency Repository
 * @param apiInterface: Api service
 * @param currencyDao: Currency DAO
 */
class CurrencyRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val currencyDao: CurrencyDao
) : ICurrencyRepository, ThreadProvider {

    /**
     * Perform network call to get the list of supported currencies
     * @param accessKey: api key
     */
    override fun getRemoteSupportedCurrencyList(): Observable<Map<String, String>> {
        return apiInterface
            .getSupportedCurrencyList(Constant.API_KEY)
            .flatMap {
                Observable.just(it.currencies)
            }
    }

    /**
     * Return live data of currency list, it is from DAO
     */
    override fun getLocalSupportedCurrencyList(): LiveData<List<Currency>> {
        return currencyDao.getCurrencyList()
    }

    /**
     * Use to upload a list of currency
     * @param time: Time of this record
     * @param map: The map of code & name
     */
    override fun updateLocalDB(time: Long, map: Map<String, String>) {
        val list = arrayListOf<Currency>()
        map.keys.forEach { key ->
            map[key]?.let { value ->
                list.add(Currency(key, value, time))
            }
        }
        currencyDao.insertCurrencyList(list)
    }
}

/**
 * Implementation of currency dao class
 */
class CurrencyDaoImpl(private val database: AppDatabase) : CurrencyDao {

    override fun getCurrencyList(): LiveData<List<Currency>> {
        return database.currencyDao().getCurrencyList()
    }

    override fun insertOrUpdateCurrency(currency: Currency): Long {
        return database.currencyDao().insertOrUpdateCurrency(currency)
    }

    override fun deleteCurrency(currency: Currency) {
        return database.currencyDao().deleteCurrency(currency)
    }

    override fun insertCurrencyList(list: List<Currency>) {
        return database.currencyDao().insertCurrencyList(list)
    }
}

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM Currency")
    fun getCurrencyList(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateCurrency(currency: Currency): Long

    @Delete
    fun deleteCurrency(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyList(list: List<Currency>)
}

/**
 * The interface to define actions: get currency from remote and store in locals
 */
interface ICurrencyRepository {
    fun getRemoteSupportedCurrencyList(): Observable<Map<String, String>>
    fun getLocalSupportedCurrencyList(): LiveData<List<Currency>>
    fun updateLocalDB(time: Long, map: Map<String, String>)
}
