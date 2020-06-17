package com.leiming.arouterdemo.ui.home.network

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RequestService {

    @Headers("Authorization:APPCODE fba0dd36693b4b04a6706ba0a2302d7f")
    @GET("/weather/city")
    suspend fun getDatas(): String
}