package com.mopo.sample

import MainViewModel
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mopo.sample.data.AppDatabase
import com.mopo.sample.data.DataItem
import com.mopo.sample.data.OfflineRepository
import com.mopo.sample.network.BaseApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ServiceLocator {

    @Volatile
    private var repository: OfflineRepository? = null
    private const val BASE_URL = "https://66f37ed970505c7982721d0d.mockapi.io/"

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

    fun provideApiService(): BaseApi {
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()
val baseURL ="https://66f5d3bb436827f15fe1df76.mockapi.io/api/v1/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)   // TODO: replace with mock server or real API
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(BaseApi::class.java)
    }

    fun provideMainViewModel(application: Application): MainViewModel {
        val repo = provideRepository(application) // your existing repo provider
        return MainViewModel(application, repo)
    }

}
