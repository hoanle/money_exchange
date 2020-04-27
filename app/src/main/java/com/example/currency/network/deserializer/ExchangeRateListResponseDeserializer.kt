package com.example.currency.network.deserializer

import com.example.currency.network.response.ExchangeRateListResponse
import com.example.currency.util.getBoolean
import com.example.currency.util.getLong
import com.example.currency.util.getString
import com.example.currency.util.getStringFloatHapMash
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Class to define how Camera json object should be parsed to Camera object
 * To handle some null cases
 */
class ExchangeRateListResponseDeserializer : JsonDeserializer<ExchangeRateListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ExchangeRateListResponse {
        return (json as? JsonObject)?.let {
            ExchangeRateListResponse(
                it.getBoolean("success"),
                it.getString("terms"),
                it.getLong("timestamp"),
                it.getString("privacy"),
                it.getString("source"),
                it.getAsJsonObject("quotes").getStringFloatHapMash()
            )
        } ?: throw JsonParseException("Invalid json: $json")
    }
}

