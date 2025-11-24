package com.mopo.sample.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Insert
    suspend fun insert(item: DataItem)

    @Query("SELECT * FROM data_items WHERE synced = 0")
    suspend fun getPendingItems(): List<DataItem>


    @Query("UPDATE data_items SET synced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)
}
