package com.mopo.sample.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
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
                dao.markAsSynced(pending.map { it.id })
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
