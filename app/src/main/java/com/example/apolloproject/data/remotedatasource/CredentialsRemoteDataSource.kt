package com.example.apolloproject.data.remotedatasource

import com.apolloproject.GetAllTablesByCustomerQuery
import com.example.apolloproject.data.apollo.graphql.mappers.toTableGraphql
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
) {
    suspend fun getLogin(mail: String, password: String): NetworkResult<AuthenticationResponse> {
        try {
            val response =
                credentialApi.login(mail, password)
            if (response.isSuccessful()) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error("No data")
            } else {
                return NetworkResult.Error(
                    response.message()
                )

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }


suspend fun doRegister(mail: String, password: String): NetworkResult<Boolean> {
    try {
        val response =
            credentialApi.register(mail, password)
        if (response.isSuccessful()) {
            val body = response.body()
            body?.let {
                return NetworkResult.Success(it)
            }
            error("No data")
        } else {
            return NetworkResult.Error(
                response.message()
            )

        }
    } catch (e: Exception) {
        return NetworkResult.Error(e.message ?: e.toString())
    }
}
}