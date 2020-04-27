package com.example.currency.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.model.ExchangeRate

object BindingUtil {

    @BindingAdapter("adapterToRv")
    @JvmStatic
    fun <T : RecyclerView.ViewHolder> setAdapterToRecyclerView(
        rv: RecyclerView,
        adapter: RecyclerView.Adapter<T>
    ) {
        rv.adapter = adapter
    }

    @BindingAdapter("layoutManagerToRv")
    @JvmStatic
    fun setLayoutManagerToRecyclerView(rv: RecyclerView, layoutManager: GridLayoutManager) {
        rv.layoutManager = layoutManager
    }

    @BindingAdapter(
        value = ["selectedExchangeRate", "currentExchangeRate", "amount"],
        requireAll = true
    )
    @JvmStatic
    fun updateDataToText(
        tv: TextView,
        selectedExchange: ExchangeRate?,
        currentExchangeRate: ExchangeRate?,
        amount: Float?
    ) {
        if (selectedExchange != null && currentExchangeRate != null && amount != null) {
            val value = amount * currentExchangeRate.value / selectedExchange.value
            tv.text = "$value ${currentExchangeRate.mergedCode}"
        } else {
            tv.text = ""
        }
    }
}