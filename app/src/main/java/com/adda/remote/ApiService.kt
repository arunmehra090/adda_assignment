package com.adda.remote

import com.adda.models.UserInfoResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("public-api/users")
    fun getUserInfo(): Call<UserInfoResponseModel>
}