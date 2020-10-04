package com.example.loginsignupmvvm.data.network

import com.example.loginsignupmvvm.data.responses.LoginResponse
import retrofit2.http.GET

interface UserApi {

    @GET("user")
    suspend fun getUser() : LoginResponse
}