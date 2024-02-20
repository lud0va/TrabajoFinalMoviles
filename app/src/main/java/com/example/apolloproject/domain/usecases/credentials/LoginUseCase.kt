package com.example.apolloproject.domain.usecases.credentials

import androidx.lifecycle.asLiveData
import com.example.apolloproject.data.CredentialsRepository
import com.example.apolloproject.data.model.AuthenticationResponse
import com.example.apolloproject.utils.DataStoreTokens
import com.example.apolloproject.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val credentialsRepository: CredentialsRepository,
   val  dataStoreTokens: DataStoreTokens
) {
      suspend fun invoke(username: String, password: String): Flow<NetworkResult<AuthenticationResponse>> {

       val  result=  credentialsRepository.doLogin(username, password)

        result.collect{result->
            if (result is NetworkResult.Success){
                result.data?.accessToken?.let { dataStoreTokens.saveAccessToken(it)
                result.data?.refreshToken?.let { dataStoreTokens.saveRefreshToken(it) }

                }
            }
        }
          return result
    }
}