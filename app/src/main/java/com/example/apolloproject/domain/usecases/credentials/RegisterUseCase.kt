package com.example.apolloproject.domain.usecases.credentials

import com.example.apolloproject.data.CredentialsRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(val credentialsRepository: CredentialsRepository) {
    suspend fun invoke(username:String,passw:String)=credentialsRepository.doRegister(username, passw)
}