package com.example.currency.network.response

import com.google.gson.annotations.SerializedName

data class ExchangeRateListResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("terms")
    val terms: String,

    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("privacy")
    val privacy: String,

    @SerializedName("source")
    val source: String,

    @SerializedName("quotes")
    val quotes: HashMap<String, Float>
)
