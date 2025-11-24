package com.morrison.recipeapp.data.services

import com.morrison.recipeapp.domain.dtos.AuthResponse
import com.morrison.recipeapp.domain.dtos.Login
import com.morrison.recipeapp.domain.dtos.Register
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface AuthService {

    @POST("auth/register")
    suspend fun register(@Body register : Register) : AuthResponse

    @POST("auth/login")
    suspend fun login(@Body login: Login) : AuthResponse
}