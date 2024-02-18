package com.example.apolloproject.common

sealed class ServiceResult<out T> {
    data class Success<out T>(val data: T) : ServiceResult<T>()
    data class ErrorResult(val mesagge: String ) : ServiceResult<Nothing>()
}
