package com.example.currency.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.databinding.ExchangeRateItemBinding
import com.example.currency.model.ExchangeRate

class ExchangeRateAdapter : RecyclerView.Adapter<ExchangeRateHolder>() {

    private var list: ArrayList<ExchangeRate> = arrayListOf()
    private var amount: Float = 0f
    private var selectedExchangeRate: ExchangeRate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateHolder {
        val binding = DataBindingUtil
            .inflate<ExchangeRateItemBinding>(
                LayoutInflater.from(parent.context), R.layout.exchange_rate_item,
                parent, false
            )

        return ExchangeRateHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ExchangeRateHolder, position: Int) {
        holder.binding.currentExchangeRate = list[position]
        holder.binding.selectedExchangeRate = selectedExchangeRate
        holder.binding.amount = amount
        holder.binding.executePendingBindings()
    }

    fun updateData(amount: Float, selectedExchangeRate: ExchangeRate, data: List<ExchangeRate>) {
        Log.d("Hoan", "updateData $amount")
        this.amount = amount
        this.selectedExchangeRate = selectedExchangeRate
        if (list.isEmpty()) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }
}

class ExchangeRateHolder(val binding: ExchangeRateItemBinding) :
    RecyclerView.ViewHolder(binding.root)