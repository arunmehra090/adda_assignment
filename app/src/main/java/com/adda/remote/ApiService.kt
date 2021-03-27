package com.adda.remote

import com.adda.models.UserInfoResponseModel
import com.adda.roomdb.entities.UserInfoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("public-api/users")
    suspend fun getUserInfoByLiveData(): Response<UserInfoResponseModel>
}