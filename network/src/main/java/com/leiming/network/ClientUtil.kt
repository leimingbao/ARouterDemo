package com.leiming.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class ClientUtil {

    fun buildRestAdapter(context: Context): RetrofitBuilder {
        val okHttpClient = OkHttpClient()
        return RetrofitBuilder(context, okHttpClient)
    }

    class RetrofitBuilder(
        context: Context,
        private val httpClient: OkHttpClient
    ) {
        private val interceptors: MutableList<Interceptor> = ArrayList()
        private val converters: MutableList<Converter.Factory> = ArrayList()
        private var followRedirects = true
        private var connectTimeoutInSeconds: Long = 0
        private var readTimeoutInSeconds: Long = 0
        private var writeTimeoutInSeconds: Long = 0
        private var baseUrl: String? = null
        private var callbackExecutor: ExecutorService? = null
        private var httpExecutor: ExecutorService? = null
        private var connectionSpecs: List<ConnectionSpec>
        private var certificatePinner: CertificatePinner? = null
        private var loggingIntercept: LoggingIntercept = LoggingIntercept(context)

        init {
            connectionSpecs = listOf(
                ConnectionSpec.MODERN_TLS,
                ConnectionSpec.CLEARTEXT
            ) // enable TLS 1.0/1.1/1.2 for https, cleartext for http
        }

        fun addInterceptor(interceptor: Interceptor): RetrofitBuilder {
            interceptors.add(interceptor)
            return this
        }

        fun addConverter(factory: Converter.Factory): RetrofitBuilder {
            converters.add(factory)
            return this
        }

        fun followRedirects(followRedirects: Boolean): RetrofitBuilder {
            this.followRedirects = followRedirects
            return this
        }

        fun connectTimeoutInSeconds(connectTimeoutInSeconds: Long): RetrofitBuilder {
            this.connectTimeoutInSeconds = connectTimeoutInSeconds
            return this
        }

        fun readTimeoutInSeconds(readTimeoutInSeconds: Long): RetrofitBuilder {
            this.readTimeoutInSeconds = readTimeoutInSeconds
            return this
        }

        fun writeTimeoutInSeconds(writeTimeoutInSeconds: Long): RetrofitBuilder {
            this.writeTimeoutInSeconds = writeTimeoutInSeconds
            return this
        }

        fun baseUrl(baseUrl: String?): RetrofitBuilder {
            this.baseUrl = baseUrl
            return this
        }

        fun callbackExecutor(executorService: ExecutorService?): RetrofitBuilder {
            callbackExecutor = executorService
            return this
        }

        fun httpExecutor(executorService: ExecutorService?): RetrofitBuilder {
            httpExecutor = executorService
            return this
        }

        fun connectionSpecs(connectionSpecs: ConnectionSpec): RetrofitBuilder {
            this.connectionSpecs = listOf(connectionSpecs)
            return this
        }

        fun addCertificatePinner(certificatePinner: CertificatePinner?): RetrofitBuilder {
            this.certificatePinner = certificatePinner
            return this
        }

        fun build(): Retrofit {

            val okHttpBuilder: OkHttpClient.Builder = httpClient.newBuilder()
            okHttpBuilder.hostnameVerifier(HostnameVerifier { hostname, session -> //强行返回true 即验证成功
                true
            })
            for (interceptor in interceptors) {
                okHttpBuilder.addInterceptor(interceptor)
            }
            okHttpBuilder.addInterceptor(loggingIntercept)
            okHttpBuilder.followRedirects(followRedirects)
                .readTimeout(readTimeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutInSeconds, TimeUnit.SECONDS)
                .connectTimeout(connectTimeoutInSeconds, TimeUnit.SECONDS)
            if (httpExecutor != null) {
                okHttpBuilder.dispatcher(Dispatcher(httpExecutor!!))
            }
//            okHttpBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager)
            okHttpBuilder.connectionSpecs(connectionSpecs) // configure OkHttp to restrict TLS versions
            val retrofitBuilder = Retrofit.Builder()
            retrofitBuilder.client(okHttpBuilder.build())
            var baseUrl = baseUrl
            if (baseUrl != null && baseUrl.isNotEmpty()) {
                if (!baseUrl.endsWith("/")) {
                    baseUrl += "/"
                }
                retrofitBuilder.baseUrl(baseUrl)
            }
            if (callbackExecutor != null) {
                retrofitBuilder.callbackExecutor(callbackExecutor)
            }
            for (converter in converters) {
                retrofitBuilder.addConverterFactory(converter)
            }

            retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
            retrofitBuilder.addCallAdapterFactory(CoroutineCallAdapterFactory())

            return retrofitBuilder.build()
        }
    }
}