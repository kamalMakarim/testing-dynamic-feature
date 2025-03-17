package com.kamal.nodynamicfeature.request

import com.kamal.nodynamicfeature.request.RetrofitClient.getClient

object UtilsApi {
    const val BASE_URL_API: String = "http://10.0.2.2:3010/"
    val apiService: BaseApiService
        get() = getClient(BASE_URL_API)!!.create(BaseApiService::class.java)
}