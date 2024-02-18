package com.example.apolloproject.data.retrofit



import com.example.apolloproject.utils.NetworkResult
import retrofit2.Response



abstract class BaseApiResponse {

    suspend fun <T,R> safeApiCall(apiCall: suspend () -> Response<R>,transform :(R) -> T ): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(transform(body))
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun safeApiCallVoid(apiCall: suspend () -> Response<Void>): NetworkResult<Unit> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                return NetworkResult.Success(Unit)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed" +errorMessage)

}