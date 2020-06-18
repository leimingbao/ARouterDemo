package com.leiming.arouterdemo.ui.home.network

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RequestService {

    //    @FormUrlEncoded
//    @Headers("Authorization:APPCODE fba0dd36693b4b04a6706ba0a2302d7f")
//    @POST("/weather/query")
    @GET("data/sk/{cityId}.html")
    suspend fun getDatas(@Path("cityId") city: String): JsonObject
}