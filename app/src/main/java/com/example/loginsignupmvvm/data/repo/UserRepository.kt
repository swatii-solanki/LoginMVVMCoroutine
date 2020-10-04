package com.example.loginsignupmvvm.data.repo

import com.example.loginsignupmvvm.data.network.UserApi

class UserRepository(private val api: UserApi) : BaseRepository() {

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }
}