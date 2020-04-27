package com.example.currency.util

import android.content.Context
import com.example.currency.model.Currency
import com.example.currency.model.ExchangeRate

class Util {
    companion object {

        fun needRefresh(context: Context): Boolean {
            val pref = context.getSharedPreferences("money_conversion", 0)
            val lastTime = pref.getLong("last_time", 0)
            if (System.currentTimeMillis() - lastTime > 30 * 60 * 1000) {
                pref.edit().putLong("last_time", System.currentTimeMillis()).commit()
                return true
            } else {
                return false
            }
        }

        fun findSelectedExchange(currency: Currency, data: List<ExchangeRate>): ExchangeRate? {
            data.forEach {
                if (it.mergedCode == currency.code) {
                    return it
                }
            }
            return null
        }
    }
}