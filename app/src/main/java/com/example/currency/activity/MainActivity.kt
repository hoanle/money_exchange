package com.example.currency.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currency.R
import com.example.currency.fragment.MoneyConversionFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    //Inject the money conversion fragment and use it to the container
    private val moneyConversionFragment: MoneyConversionFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, moneyConversionFragment)
            .commit()
    }
}
