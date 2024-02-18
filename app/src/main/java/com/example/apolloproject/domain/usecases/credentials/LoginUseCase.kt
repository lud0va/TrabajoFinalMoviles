package com.example.apolloproject.domain.usecases.credentials

import com.example.apolloproject.data.CredentialsRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(val credentialsRepository: CredentialsRepository) {
    suspend fun invoke(username:String,password:String)=credentialsRepository.doLogin(username, password)
}