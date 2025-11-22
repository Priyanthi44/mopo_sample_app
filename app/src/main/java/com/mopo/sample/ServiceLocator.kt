package com.mopo.sample

import android.content.Context
import androidx.room.Room
import com.mopo.sample.data.AppDatabase
import com.mopo.sample.data.OfflineRepository
import com.mopo.sample.network.BaseApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ServiceLocator {

    @Volatile
    private var repository: OfflineRepository? = null

    fun provideRepository(context: Context): OfflineRepository {
        return repository ?: synchronized(this) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "mopo-sample-db"
            ).fallbackToDestructiveMigration()
                .build()

            val api = provideApiService()

            val repo = OfflineRepository(
                dao = db.dataDao(),
                api = api,
                context = context.applicationContext
            )

            repository = repo
            repo
        }
    }

    private fun provideApiService(): BaseApi {
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://example.com/")   // TODO: replace with mock server or real API
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(BaseApi::class.java)
    }
}
