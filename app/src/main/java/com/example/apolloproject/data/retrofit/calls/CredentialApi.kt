package com.example.apolloproject.data.retrofit.calls


import com.example.apolloproject.data.model.AuthenticationResponse
import com.example.apolloproject.data.retrofit.ConfiguracionRetrofit
import dagger.Component
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CredentialApi {

    @GET("/login")
    suspend fun login(@Query("username") username:String, @Query("password") password:String ):Response<AuthenticationResponse>
    @GET("/register")
    suspend fun register(@Query("username") username:String, @Query("password") password:String ):Response<Boolean>

    @GET("/getAccessToken")
    suspend fun getAccesToken(@Query("refreshtoken") refreshTokenn:String ):Response<String>


}