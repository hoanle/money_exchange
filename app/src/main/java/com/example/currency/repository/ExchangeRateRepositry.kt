package com.example.currency.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.currency.local.AppDatabase
import com.example.currency.model.ExchangeRate
import com.example.currency.network.ApiInterface
import com.example.currency.network.response.ExchangeRateListResponse
import com.example.currency.util.Constant
import io.reactivex.Observable

/**
 * Concrete class of Exchange Rate Repository
 * @param apiInterface: Api service
 * @param exchangeRateDao: Exchange Rate DAO
 */
class ExchangeRateRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val exchangeRateDao: ExchangeRateDao
) :
    IExchangeRateRepository {

    /**
     * Perform network call to get the list of exchange rate
     */
    override fun getRemoteExchangeRateList(): Observable<ExchangeRateListResponse> {
        return apiInterface
            .getExchangeRateList(Constant.API_KEY)
    }

    override fun getLocalExchangeRateList(): LiveData<List<ExchangeRate>> {
        return exchangeRateDao.getExchangeRateList()
    }

    /**
     * Use to upload a list of exchange rate
     * @param time: Time of this record
     * @param map: The map of code & value
     */
    override fun updateLocalDB(timestamp: Long, map: Map<String, Float>) {
        val list = arrayListOf<ExchangeRate>()
        map.keys.forEach { key ->
            map[key]?.let { value ->
                list.add(ExchangeRate(key.replaceFirst("USD", ""), value, timestamp))
            }
        }
        exchangeRateDao.insertExchangeRateList(list)
    }
}

/**
 * Implementation of exchange dao class
 */
class ExchangeRateDaoImpl(private val database: AppDatabase) : ExchangeRateDao {

    override fun getExchangeRateList(): LiveData<List<ExchangeRate>> {
        return database.exchangeRateDao().getExchangeRateList()
    }

    override fun insertOrUpdateExchangeRate(exchangeRate: ExchangeRate): Long {
        return database.exchangeRateDao().insertOrUpdateExchangeRate(exchangeRate)
    }

    override fun deleteExchangeRate(exchangeRate: ExchangeRate) {
        return database.exchangeRateDao().deleteExchangeRate(exchangeRate)
    }

    override fun insertExchangeRateList(list: List<ExchangeRate>) {
        return database.exchangeRateDao().insertExchangeRateList(list)
    }
}

@Dao
interface ExchangeRateDao {
    @Query("SELECT * FROM ExchangeRate")
    fun getExchangeRateList(): LiveData<List<ExchangeRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateExchangeRate(exchangeRate: ExchangeRate): Long

    @Delete
    fun deleteExchangeRate(exchangeRate: ExchangeRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExchangeRateList(list: List<ExchangeRate>)
}


/**
 * The interface to define actions: get exchange from remote and store in locals
 */
interface IExchangeRateRepository {
    fun getRemoteExchangeRateList(): Observable<ExchangeRateListResponse>
    fun getLocalExchangeRateList(): LiveData<List<ExchangeRate>>
    fun updateLocalDB(timestamp: Long, map: Map<String, Float>)
}
