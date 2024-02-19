package com.example.apolloproject.data.apollo


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


private const val s = "http://192.168.1.24:8080"

class AuthenticationInterceptor @Inject constructor(private val dataStoreTokens: DataStoreTokens) :
    Interceptor {
    private val mutex = Mutex()
    var gson = GsonBuilder()
        .setLenient()
        .create()
    override fun intercept(chain: Interceptor.Chain): Response {
        var token: String? = null
        runBlocking {
            mutex.withLock {
                // get current token
                token = dataStoreTokens.getAccessToken().firstOrNull()
            }
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(request)

        return if (response.code == 401) {
            runBlocking {
                mutex.withLock {
                    // get new token
                    val refreshToken = dataStoreTokens.getRefreshToken().first()
                    val newToken = getNewToken(refreshToken)
                    // save new token
                    newToken.let { newAccessToken ->
                        newAccessToken?.let { dataStoreTokens.saveAccessToken(it) }
                    }

                    newToken ?: token



                }
            }.let { newToken ->
                // retry request with new token
                val newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $newToken")
                    .build()
                chain.proceed(newRequest)

            }
        } else {
            response
        }
    }

    private suspend fun getNewToken(refreshToken: String?): String? {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ConstantesServer.IPSERVIDORAUTH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        val service = retrofit.create(CredentialApi::class.java)
        val response = service.getAccesToken("$refreshToken")

        return if (response.isSuccessful) {
            response.body()
        } else {
            null

        }
    }
}