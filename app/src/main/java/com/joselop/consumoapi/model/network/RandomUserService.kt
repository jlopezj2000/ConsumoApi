package com.joselop.consumoapi.model.network

import com.joselop.consumoapi.model.User

import retrofit2.Response
import retrofit2.http.GET

// Define la respuesta esperada desde la API
interface RandomUserService {
    @GET("?results=20")
    suspend fun getUsers(): Response<UserResponse>
}


data class UserResponse(
    val results: List<User>
)