package com.leiming.network

import android.content.Context
import androidx.room.Room
import com.leiming.network.database.AppDataBase
import com.leiming.network.database.Logging
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.String


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
        println("request URL : " + request.url)
        val response = chain.proceed(request)
        val responseBody = response.peekBody(1024 * 1024.toLong())
        val loggingEntity = Logging(1, "" + request.url, responseBody.string())
        dao.insert(loggingEntity)
        return response
    }
}