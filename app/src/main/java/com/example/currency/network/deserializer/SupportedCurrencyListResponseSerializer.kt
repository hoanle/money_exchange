package com.example.currency.network.deserializer

import com.example.currency.network.response.SupportedCurrencyListResponse
import com.example.currency.util.getBoolean
import com.example.currency.util.getString
import com.example.currency.util.getStringStringHapMash
import com.google.gson.*
import java.lang.reflect.Type


/**
 * Class to define how Camera json object should be parsed to Camera object
 * To handle some null cases
 */
class SupportedCurrencyListResponseSerializer : JsonDeserializer<SupportedCurrencyListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SupportedCurrencyListResponse {
        return (json as? JsonObject)?.let {
            SupportedCurrencyListResponse(
                it.getBoolean("success"),
                it.getString("terms"),
                it.getString("privacy"),
                it.getAsJsonObject("currencies").getStringStringHapMash()
            )
        } ?: throw JsonParseException("Invalid json: $json")
    }
}
