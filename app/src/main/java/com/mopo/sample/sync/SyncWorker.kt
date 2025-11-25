package com.mopo.sample.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mopo.sample.ServiceLocator.provideRepository
import java.util.concurrent.TimeUnit

class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val repo = provideRepository(applicationContext)
        return try {
            repo.performImmediateSync()       // 🔹 Call your real sync function
            Result.success()                  // 🔹 Report success
        } catch (e: Exception) {
            Result.retry()                    // 🔹 Retry if failed
        }
    }

    companion object {
        fun schedulePeriodic(context: Context) {
            val periodic = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
                .setConstraints(
                    androidx.work.Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                ).build()


            WorkManager.getInstance(context).enqueueUniquePeriodicWork("periodic_sync", ExistingPeriodicWorkPolicy.KEEP, periodic)
        }
    }
}
