package com.leiming.network

import android.content.Context
import androidx.room.Room
import com.leiming.network.database.AppDataBase
import com.leiming.network.database.Logging
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class LoggingIntercept constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val dataBase: AppDataBase = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "Logging.db"
        )
            .allowMainThreadQueries()
            .build()

        val dao = dataBase.getLoggingDao()

        val request = chain.request()
        val loggingEntity = Logging(1, "" + request.url, "2")

        println("request URL : " + request.url)
        dao.insert(loggingEntity)
        println("insert success")
        val response = chain.proceed(request)
        println("response message : " + response.message)
        return response
    }
}