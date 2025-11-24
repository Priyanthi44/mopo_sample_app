package com.mopo.sample.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "data_items")
@JsonClass(generateAdapter = true)
data class DataItem(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id") val id: Long = 0,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "synced") val synced: Boolean = false
)
