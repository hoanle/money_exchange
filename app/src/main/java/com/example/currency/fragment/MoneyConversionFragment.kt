package com.example.currency.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.currency.R
import com.example.currency.adapter.ExchangeRateAdapter
import com.example.currency.databinding.FragmentMoneyConversionBinding
import com.example.currency.model.Currency
import com.example.currency.model.ExchangeRate
import com.example.currency.util.Util
import com.example.currency.view_model.CurrencyViewModel
import com.example.currency.view_model.ExchangeRateViewModel
import org.koin.android.ext.android.get

class MoneyConversionFragment(
    private val currencyViewModel: CurrencyViewModel,
    private val exchangeRateViewModel: ExchangeRateViewModel
) : Fragment(),
    FragmentBindingInterface<FragmentMoneyConversionBinding> {

    private lateinit var binding: FragmentMoneyConversionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_money_conversion,
            null,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performBinding(binding)
        registerLiveData()
        handleViews()
    }

    private fun handleViews() {
        val et = requireView().findViewById<EditText>(R.id.etInput)
        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                try {
                    val value = p0.toString().toFloat()
                    exchangeRateViewModel.updateAmount(value)
                } catch (e: Exception) {
                    e.printStackTrace()
                    exchangeRateViewModel.updateAmount(0f)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    /**
     * Register all LiveData to this
     */
    private fun registerLiveData() {
        val tv = requireView().findViewById<TextView>(R.id.tvCurrency)

        tv.setOnClickListener {
            val fragment = get<CurrencyPickerDialogPopup>()
            fragment.show(childFragmentManager, "")
        }

        currencyViewModel.getCurrencyList().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty() || Util.needRefresh(requireContext())) {
                currencyViewModel.getRemoteSupportCurrencyList()
            }
        })

        MediatorLiveData<Pair<Float, Pair<ExchangeRate, List<ExchangeRate>>>>().apply {

            var list: List<ExchangeRate>? = null
            var currency: Currency? = null
            var amount: Float? = null

            fun checkValue() {
                if ((list?.size ?: 0) > 0
                    && currency != null && (amount ?: 0f) >= 0f
                ) {
                    val exchange = Util.findSelectedExchange(currency!!, list!!)
                    exchange?.let {
                        value = Pair(amount ?: 0f, Pair(it, list!!))
                    }
                }
            }
            addSource(exchangeRateViewModel.getExchangeRate()) {
                if (it.isEmpty() || Util.needRefresh(requireContext())) {
                    exchangeRateViewModel.requestRemoteExchangeRate()
                }
                list = it
                checkValue()
            }

            addSource(exchangeRateViewModel.getSelectedCurrency()) {
                if (it != null) {
                    tv.text = it.name
                }
                currency = it
                checkValue()
            }

            addSource(exchangeRateViewModel.getAmount()) {
                amount = it
                checkValue()
            }
        }.observe(viewLifecycleOwner, Observer {
            binding.adapter?.updateData(it.first, it.second.first, it.second.second)
        })
    }

    /**
     * Bind data to view
     * @param binding: Binding of current view
     */
    override fun performBinding(binding: FragmentMoneyConversionBinding) {
        binding.adapter = ExchangeRateAdapter()
        binding.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.executePendingBindings()
    }
}