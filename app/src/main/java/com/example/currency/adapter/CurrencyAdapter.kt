package com.example.currency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.databinding.ItemCurrencyBinding
import com.example.currency.model.Currency

class CurrencyAdapter(private val callback: (Currency) -> Unit) :
    RecyclerView.Adapter<CurrencyHolder>() {

    var list: ArrayList<Currency> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CurrencyHolder {
        val binding = DataBindingUtil
            .inflate<ItemCurrencyBinding>(
                LayoutInflater.from(parent.context), R.layout.item_currency,
                parent, false
            )

        return CurrencyHolder(binding, callback)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<Currency>) {
        val diffCallback = CurrencyDiffUtilCallback(list, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.binding.currency = list[position]
        holder.binding.executePendingBindings()
    }
}

class CurrencyHolder(
    val binding: ItemCurrencyBinding,
    private val callback: (Currency) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        this.binding.root.setOnClickListener {
            callback.invoke(binding.currency!!)
        }
    }
}