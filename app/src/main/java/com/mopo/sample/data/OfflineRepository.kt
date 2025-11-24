package com.mopo.sample.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mopo.sample.ServiceLocator.provideApiService
import com.mopo.sample.network.BaseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineRepository(private val dao: DataDao, private val api: BaseApi, private val context: Context) {

    suspend fun insertItem(item: DataItem) = withContext(Dispatchers.IO) {
        dao.insert(item)
    }

    fun enqueueSync() {
        val work = OneTimeWorkRequestBuilder<com.mopo.sample.sync.SyncWorker>().build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork("sync_work", ExistingWorkPolicy.KEEP, work)
    }

    suspend fun performImmediateSync() = withContext(Dispatchers.IO) {
        val pending = dao.getPendingItems()
        if (pending.isNotEmpty()) {
            try {
                api.uploadItems(pending)
                dao.markAsSynced(pending.map { it.id})
            } catch (e: Exception) {
                throw e
            }
        }
    }
    suspend fun uploadToApi(item: DataItem): Result<DataItem> {
        return try {
            val response = provideApiService().postItem(item)

            if (response.isSuccessful) {
                val uploadedItem = response.body()
                if (uploadedItem != null) {
                    try {
                        // Use uploadedItem.id because MockAPI generates an ID
                        dao.markAsSyncedOne(uploadedItem.id)
                    } catch (e: Exception) {
                        return Result.failure(Exception("Synced but DB update failed: ${e.message}"))
                    }
                    Result.success(uploadedItem)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Upload failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
