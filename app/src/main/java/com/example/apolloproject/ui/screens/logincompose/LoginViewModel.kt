package com.example.apolloproject.ui.screens.logincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.R
import com.example.apolloproject.domain.usecases.credentials.LoginUseCase
import com.example.apolloproject.domain.usecases.credentials.RegisterUseCase
import com.example.apolloproject.domain.usecases.customers.GetCustomersUseCase
import com.example.apolloproject.utils.NetworkResult
import com.example.apolloproject.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,



    ) : ViewModel() {
    private val _uiState= MutableStateFlow(LoginContract.State())
    val uiState: StateFlow<LoginContract.State> = _uiState.asStateFlow()



    fun event(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.CambiarPasswState -> cambiarPasswState(event.passw)
            is LoginContract.Event.CambiarUserState -> cambiarUserState(event.username)
            LoginContract.Event.login -> doLogin()
            LoginContract.Event.register -> doRegister()
            is LoginContract.Event.CambiarLoginSuccess -> cambiarLoginSuccess(event.flag)
        }
    }
    fun cambiarLoginSuccess(boolean: Boolean){
        _uiState.update {
            it.copy(loginsucces = boolean)
        }
    }

    fun cambiarUserState(user: String) {
        _uiState.update {
            it.copy(username = user)
        }
    }

    fun cambiarPasswState(passw: String) {
        _uiState.update {
            it.copy(password = passw)
        }

    }

    fun doLogin() {
        viewModelScope.launch {
            if (!uiState.value.username.isNullOrBlank()|| !uiState.value.password.isNullOrBlank()){
                uiState.value.username?.let {
                    uiState.value.password?.let { it1 ->
                        loginUseCase.invoke(
                            it,
                            it1
                        ).catch (action = {
                            cause ->
                            _uiState.update {
                                it.copy(
                                    message=cause.message

                                )
                            }
                        })

                        .collect{
                                result->
                            when (result) {
                                is NetworkResult.Error -> {
                                    _uiState.update {
                                        it.copy(
                                            message = result.message,

                                            )
                                    }
                                }

                                is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true ) }

                                is NetworkResult.Success ->_uiState.update { it.copy(loginsucces = true, message = stringProvider.getString(R.string.loginCompl)) }

                            }
                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(message = stringProvider.getString(R.string.introducirNamePasw))
                }
            }
        }


    }

    fun doRegister() {
        viewModelScope.launch {
            if (!uiState.value.username.isNullOrBlank() || !uiState.value.password.isNullOrBlank()) {

                uiState.value.username?.let {
                    uiState.value.password?.let { it1 ->
                        registerUseCase.credentialsRepository.doRegister(
                            it,
                            it1
                        ).collect { result ->
                            when (result) {
                                is NetworkResult.Error -> {
                                    _uiState.update {
                                        it.copy(
                                            message = result.message,

                                            )
                                    }
                                }

                                is NetworkResult.Loading -> _uiState.update { it.copy() }
                                is NetworkResult.Success -> _uiState.update { it.copy(message = stringProvider.getString(R.string.registroCompl), registersuccess = true) }

                            }
                        }


                    }
                }

            }else{
                _uiState.update { it.copy(message = stringProvider.getString(R.string.usuarInv), registersuccess = true) }

            }

        }

    }

}