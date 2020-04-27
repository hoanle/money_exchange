package com.example.currency.di

import androidx.room.Room
import com.example.currency.fragment.CurrencyPickerDialogPopup
import com.example.currency.fragment.MoneyConversionFragment
import com.example.currency.local.AppDatabase
import com.example.currency.network.ApiClient
import com.example.currency.repository.*
import com.example.currency.view_model.CurrencyViewModel
import com.example.currency.view_model.ExchangeRateViewModel
import org.koin.dsl.module

val repositoryModules = module {
    factory<ICurrencyRepository> {
        CurrencyRepositoryImpl(apiInterface = get(), currencyDao = get())
    }
    factory<IExchangeRateRepository> {
        ExchangeRateRepositoryImpl(apiInterface = get(), exchangeRateDao = get())
    }
}

val networkModule = module {
    single {
        ApiClient().getService()
    }
}
val viewModelModules = module {
    single {
        CurrencyViewModel(currencyRepository = get())
    }

    single {
        ExchangeRateViewModel(exchangeRepository = get())
    }
}

val fragmentModules = module {
    factory {
        MoneyConversionFragment(currencyViewModel = get(), exchangeRateViewModel = get())
    }

    factory {
        CurrencyPickerDialogPopup(currencyViewModel = get(), exchangeRateViewModel = get())
    }
}

val daoModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "currency_database"
        ).build()
    }

    single<CurrencyDao> { CurrencyDaoImpl(database = get()) }
    single<ExchangeRateDao> { ExchangeRateDaoImpl(database = get()) }
}


// Merge all modules to app modules list
val appModules = listOf(
    daoModules,
    networkModule,
    repositoryModules,
    viewModelModules,
    fragmentModules
)
