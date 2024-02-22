package com.example.apolloproject.data.remotedatasource

import com.example.apolloproject.common.Constantes
import com.example.apolloproject.data.model.AuthenticationResponse
import com.example.apolloproject.data.retrofit.calls.CredentialApi
import com.example.apolloproject.domain.model.Error
import com.example.apolloproject.utils.DataStoreTokens
import com.example.apolloproject.utils.NetworkResult
import com.squareup.moshi.Moshi
import javax.inject.Inject

class CredentialsRemoteDataSource @Inject constructor(
    private val credentialApi: CredentialApi,
    private val moshi: Moshi
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
                error(Constantes.NO_DATA)
            } else {
                val msgerror = response.errorBody()?.string() ?:Constantes.SPACE
                val adapter = moshi.adapter(Error::class.java)
                val error = adapter.fromJson(msgerror)
                return NetworkResult.Error(error?.msg?:Constantes.SPACE)

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
                error(Constantes.NO_DATA)
            } else {
                val msgerror = response.errorBody()?.string() ?:Constantes.SPACE
                val adapter = moshi.adapter(Error::class.java)
                val error = adapter.fromJson(msgerror)
                return NetworkResult.Error(error?.msg?:Constantes.SPACE)

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}