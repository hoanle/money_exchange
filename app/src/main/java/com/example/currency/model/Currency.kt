package com.example.currency.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey @ColumnInfo(name = "code") var code: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "timestamp") var timestamp: Long = 0
)
