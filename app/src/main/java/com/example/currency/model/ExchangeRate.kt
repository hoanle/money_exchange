package com.example.currency.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeRate(
    @PrimaryKey @ColumnInfo(name = "mergedCode") var mergedCode: String = "",
    @ColumnInfo(name = "value") var value: Float = 0f,
    @ColumnInfo(name = "timestamp") var timestamp: Long = 0
)
