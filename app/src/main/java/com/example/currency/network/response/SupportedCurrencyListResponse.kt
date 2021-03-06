package com.example.currency.network.response

import com.google.gson.annotations.SerializedName

data class SupportedCurrencyListResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("terms")
    val terms: String,

    @SerializedName("privacy")
    val privacy: String,

    @SerializedName("currencies")
    val currencies: HashMap<String, String>
)
