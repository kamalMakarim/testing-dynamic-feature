package com.kamal.testingdynamicmodule.request

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.OkHttpClient.Builder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofit: Retrofit? = null
    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(customGson))
                .build()
        }
        return retrofit
    }

    private fun okHttpClient(): OkHttpClient {
        return Builder()
            .addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
                val originalRequest: Request = chain.request()
                val newRequest: Request =
                    originalRequest.newBuilder()
                        .build()
                chain.proceed(newRequest)
            })
            .build()
    }

    private val customGson: Gson
        get() = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
}