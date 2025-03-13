package com.kamal.testingdynamicmodule.request

import com.kamal.testingdynamicmodule.request.RetrofitClient.getClient

object UtilsApi {
    const val BASE_URL_API: String = "http://10.0.2.2:5000/"
    val apiService: BaseApiService
        get() = getClient(BASE_URL_API)!!.create(BaseApiService::class.java)
}