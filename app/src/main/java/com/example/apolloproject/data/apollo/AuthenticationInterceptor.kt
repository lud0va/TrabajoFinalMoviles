package com.example.apolloproject.data.apollo


import com.example.apolloproject.data.retrofit.CacheAuthorization

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthenticationInterceptor @Inject constructor(private val cacheAuthorization: CacheAuthorization) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                cacheAuthorization.accesToken?.let { token ->
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()
        return chain.proceed(request)
    }
}

