package com.example.apolloproject.utils


import com.example.apolloproject.common.ConstantesServer
import com.example.apolloproject.data.retrofit.calls.CredentialApi
import com.example.apolloproject.utils.DataStoreTokens
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject



class AuthenticationInterceptor @Inject constructor(private val dataStoreTokens: DataStoreTokens) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreTokens.getAccessToken().first()
        }
        val request = chain.request().newBuilder().header("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }
}