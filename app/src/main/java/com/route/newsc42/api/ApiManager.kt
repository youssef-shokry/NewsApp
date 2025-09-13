package com.route.newsc42.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    val apiKey = "337dc2b5fe7c467aacde1b358cbe785"
    val baseUrl = "https://newsapi.org"
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getOkHttpClient(): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain->
                val request = chain.request().newBuilder()
                    .addHeader("X-Api-Key", apiKey)
                    .build()

                return@addInterceptor chain.proceed(request)
            }
            .build()

        return client
    }

   fun getWebServices(): WebServices{
      return retrofit.create(WebServices::class.java)
   }
}