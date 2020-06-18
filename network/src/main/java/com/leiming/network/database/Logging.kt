package com.leiming.network.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Logging(
    @PrimaryKey(autoGenerate = true)
    var logID: Int?,
    @ColumnInfo(name = "request_url")
    var url: String?,
    @ColumnInfo(name = "response_message")
    var message: String?
)