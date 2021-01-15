package com.kho.pagedv3.core

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kho.pagedv3.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteFactory {

    inline fun <reified T> createRemoteApi(url: String): T {
        val retrofit: Retrofit = createNetworkClientVerify(url, BuildConfig.DEBUG)
        return retrofit.create(T::class.java)
    }

    fun createNetworkClientVerify(
        baseUrl: String,
        debug: Boolean = false
    ): Retrofit {
        val oc = httpClient(debug)
        return retrofitClient(baseUrl, oc.build())
    }

    private fun httpClient(debug: Boolean): OkHttpClient.Builder {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        if (debug) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
                .method(originalRequest.method, originalRequest.body)
            chain.proceed(request.build())

        }

        return clientBuilder.writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
    }

    private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
}