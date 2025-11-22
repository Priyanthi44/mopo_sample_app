package com.mopo.sample.data
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class DataItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val payload: String,
    val synced: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)

