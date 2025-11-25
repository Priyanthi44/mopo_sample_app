package com.mopo.sample.network

import com.mopo.sample.data.DataItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BaseApi {
    @POST("items")
    suspend fun uploadItems(@Body items: List<DataItem>)

    @POST("items")   // must match exactly
    suspend fun postItem(@Body item: DataItem): Response<DataItem>

//    @POST("dataitem")
//    suspend fun postItem(@Body item: DataItem): DataItem
}
