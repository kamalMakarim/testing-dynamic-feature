package com.kamal.nodynamicfeature.request

import com.kamal.nodynamicfeature.model.BaseResponse
import com.kamal.nodynamicfeature.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BaseApiService {
    @GET("account/{id}")
    fun getUserId(@Path("id") id: Int): Call<BaseResponse<User>>

    @POST("account/login")
    fun login(
        @Query("email") email: String?,
        @Query("password") password: String?
    ): Call<BaseResponse<User>>
}
