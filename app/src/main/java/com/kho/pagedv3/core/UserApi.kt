package com.kho.pagedv3.core

import com.kho.pagedv3.core.model.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("/public-api/users")
    fun getUser(@Query("page") page: String = "1"): Deferred<UserResponse>
}