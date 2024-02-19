package com.example.apolloproject.data.retrofit.response

import com.example.apolloproject.data.model.AuthenticationResponse
import com.example.apolloproject.data.retrofit.BaseApiResponse
import com.example.apolloproject.data.retrofit.calls.CredentialApi
import com.example.apolloproject.utils.DataStoreTokens
import com.example.apolloproject.utils.NetworkResult
import java.lang.Exception
import javax.inject.Inject

class CredentialsRemoteDataSource @Inject constructor(
    private val credentialApi: CredentialApi,
    val dataStoreTokens: DataStoreTokens
) :
    BaseApiResponse() {
    suspend fun getLogin(mail: String, password: String): NetworkResult<AuthenticationResponse> {
        try {

            val response = credentialApi.login(mail, password)

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    dataStoreTokens.saveAccessToken(it.accessToken)
                    dataStoreTokens.saveRefreshToken(it.refreshToken)
                }
                body?.let {
                    return NetworkResult.Success(it)
                }

                error("ERROR")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "ERROR"
                error("${kotlin.error(errorMessage)} ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun doRegister(mail: String, password: String): NetworkResult<Boolean> {
        try {

            val response = credentialApi.register(mail, password)

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(response.errorBody().toString())
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error"
                error("${kotlin.error(errorMessage)} ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}