package com.adda.remote

import com.adda.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val WEB_URL = "https://gorest.co.in/"

    fun injectApiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(okHttpClient: OkHttpClient = getOkHttpClient()): Retrofit {
        return Retrofit.Builder()
                .baseUrl(WEB_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient)
                .build()
    }

    private fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun getOkHttpClient(okHttpLogger: HttpLoggingInterceptor = getHttpLogger()): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(okHttpLogger)
                .readTimeout(1000L,TimeUnit.SECONDS)
                .callTimeout(1000L, TimeUnit.SECONDS)
                .build()
    }

}