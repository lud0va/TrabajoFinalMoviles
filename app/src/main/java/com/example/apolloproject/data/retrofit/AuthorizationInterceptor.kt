package com.example.apolloproject.data.retrofit

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Credentials.basic
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


class AuthorizationInterceptor (
    val ca: CacheAuthorization,
): Interceptor{


    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        var request: Request

        request = if (ca.accesToken.isEmpty()) {
            original.newBuilder()
                .header("Authorization", basic(ca.user, ca.pass)).build()
        } else {
            original.newBuilder()
                .header("Authorization", "Bearer " + ca.accesToken).build()
        }

        var response: Response = chain.proceed(request)
        response.header("Authorization")?.let {
            ca.accesToken = it
        }

        if (!response.isSuccessful) {
            //reintentar
            response.close()
            request = original.newBuilder()
                .header("Authorization", basic(ca.user, ca.pass)).build()
            response = chain.proceed(request)
            response.header("Authorization")?.let {
                ca.accesToken = it
            }

        }

        return response
    }
}
