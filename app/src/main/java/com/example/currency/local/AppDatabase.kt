package com.example.currency.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currency.model.Currency
import com.example.currency.model.ExchangeRate
import com.example.currency.repository.CurrencyDao
import com.example.currency.repository.ExchangeRateDao
import com.example.currency.util.Constant

@Database(
    entities = [
        Currency::class,
        ExchangeRate::class
    ],
    version = Constant.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRateDao(): ExchangeRateDao
}
