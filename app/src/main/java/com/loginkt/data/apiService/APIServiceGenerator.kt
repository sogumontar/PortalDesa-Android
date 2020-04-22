package com.loginkt.data.apiService

import com.loginkt.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

 class APIServiceGenerator {

    val TIME_OUT = 120L
    val API_BASE_URL = BuildConfig.SERVER_URL

     private val client = OkHttpClient().newBuilder()
         .addInterceptor(HttpLoggingInterceptor().apply {
             level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
         })
         .readTimeout(TIME_OUT, TimeUnit.SECONDS)
         .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
         .build()
     private val retrofit = Retrofit.Builder()
         .baseUrl(API_BASE_URL)
         .client(client)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
     val services: ApiServices = retrofit.create(ApiServices::class.java)
 }