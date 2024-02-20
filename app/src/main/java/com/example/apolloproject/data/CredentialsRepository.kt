package com.example.apolloproject.data

import com.example.apolloproject.data.model.AuthenticationResponse
import com.example.apolloproject.data.remotedatasource.CredentialsRemoteDataSource
import com.example.apolloproject.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CredentialsRepository @Inject constructor(
    private val credentialsRemoteDataSource: CredentialsRemoteDataSource
) {


     fun doLogin(mail:String, password:String): Flow<NetworkResult<AuthenticationResponse>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= credentialsRemoteDataSource.getLogin(mail,password)
            emit(result)
        }.flowOn(Dispatchers.IO)

    }
    fun doRegister(mail:String,password:String): Flow<NetworkResult<Boolean>> {
        return flow {
            emit(NetworkResult.Loading())
            val result=credentialsRemoteDataSource.doRegister(mail,password)
            emit(result)


        }.flowOn(Dispatchers.IO)
    }
}