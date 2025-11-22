package com.mopo.sample.network

import retrofit2.http.Body
import retrofit2.http.POST

interface BaseApi {
    @POST("/upload")
    suspend fun uploadItems(@Body items: List<com.mopo.sample.data.DataItem>)
}
