package com.example.currency.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.currency.R
import com.example.currency.adapter.CurrencyAdapter
import com.example.currency.databinding.DialogFragmentCurrencyPickerBinding
import com.example.currency.view_model.CurrencyViewModel
import com.example.currency.view_model.ExchangeRateViewModel

/**
 * Class to select the currency to do the exchange
 */
class CurrencyPickerDialogPopup(
    private val currencyViewModel: CurrencyViewModel,
    private val exchangeRateViewModel: ExchangeRateViewModel
) :
    DialogFragment(),
    FragmentBindingInterface<DialogFragmentCurrencyPickerBinding> {

    private lateinit var binding: DialogFragmentCurrencyPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.dialog_fragment_currency_picker,
            null,
            false
        )
        performBinding(binding)
        return binding.root
    }

    /**
     * Perform data binding
     * @param binding instance of DialogFragmentCameraImageBinding
     */
    override fun performBinding(binding: DialogFragmentCurrencyPickerBinding) {
        binding.root.setOnClickListener { dismiss() }
        binding.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        binding.adapter = CurrencyAdapter {
            exchangeRateViewModel.updateSelectedCurrency(it)
            dismiss()
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyViewModel.getCurrencyList().observe(viewLifecycleOwner, Observer {
            binding.adapter?.setData(it)
        })
    }

    /**
     * To make sure dialog is fullscreen
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val dialog = Dialog(
            requireContext(),
            android.R.style.Theme_DeviceDefault_Dialog_NoActionBar
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return dialog
    }
}