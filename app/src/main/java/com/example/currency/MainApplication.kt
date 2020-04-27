package com.example.currency

import android.app.Application
import com.example.currency.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpDI()
    }

    /**
     * Start the entire app's  modules
     */
    private fun setUpDI() {
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}
