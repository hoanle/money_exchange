package com.example.currency.util

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive


fun JsonObject.getString(key: String): String {
    return (get(key) as? JsonPrimitive)?.asString ?: ""
}

fun JsonObject.getLong(key: String): Long {
    return (get(key) as? JsonPrimitive)?.asLong ?: 0L
}

fun JsonObject.getBoolean(key: String): Boolean {
    return (this.get(key) as? JsonPrimitive)?.asBoolean ?: false
}

fun JsonObject.getStringStringHapMash(): HashMap<String, String> {
    val map = HashMap<String, String>()
    val keys = this.keySet()
    for (key in keys) {
        map[key] = get(key).asString
    }
    return map
}

fun JsonObject.getStringFloatHapMash(): HashMap<String, Float> {
    val map = HashMap<String, Float>()
    val keys = this.keySet()
    for (key in keys) {
        map[key] = get(key).asFloat
    }
    return map
}
