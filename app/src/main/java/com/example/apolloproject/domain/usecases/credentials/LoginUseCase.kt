package com.example.apolloproject.domain.usecases.credentials

import androidx.lifecycle.asLiveData
import com.example.apolloproject.data.CredentialsRepository
import com.example.apolloproject.data.model.AuthenticationResponse
import com.example.apolloproject.utils.DataStoreTokens
import com.example.apolloproject.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val credentialsRepository: CredentialsRepository,

) {
    suspend fun invoke(username: String, password: String): Flow<NetworkResult<AuthenticationResponse>> {

       val  result=  credentialsRepository.doLogin(username, password)
          return result
    }
}