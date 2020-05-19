package com.PortalDesa.data.apiService

import com.PortalDesa.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIServiceGenerator {

    val TIME_OUT = 120L
    val API_BASE_URL = BuildConfig.SERVER_URL
    val API_BASE_URL_IMAGE = BuildConfig.SERVER_URL_IMAGE

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val createService: ApiServices = retrofit.create(ApiServices::class.java)

    private val retrofitImage = Retrofit.Builder()
        .baseUrl(API_BASE_URL_IMAGE)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val createServiceImage: ApiServices = retrofitImage.create(ApiServices::class.java)
}