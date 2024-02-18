package com.example.apolloproject.ui.screens.logincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.credentials.LoginUseCase
import com.example.apolloproject.domain.usecases.credentials.RegisterUseCase
import com.example.apolloproject.domain.usecases.customers.GetCustomersUseCase
import com.example.apolloproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getCustomersUseCase: GetCustomersUseCase


) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginContract.State> by lazy {
        MutableStateFlow(LoginContract.State())
    }
    val uiState: StateFlow<LoginContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

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
            if (uiState.value.username != null || uiState.value.password != null) {
                uiState.value.username?.let {
                    uiState.value.password?.let { it1 ->
                        loginUseCase.invoke(
                            it,
                            it1
                        ).collect{
                                result->
                            when (result) {
                                is NetworkResult.Error -> {
                                    _uiState.update {
                                        it.copy(
                                            error = result.message,

                                            )
                                    }
                                }

                                is NetworkResult.Loading -> _uiState.update { it.copy() }
                           //     is NetworkResult.Success ->getCustomersUseCase.invoke()
                                is NetworkResult.Success ->_uiState.update { it.copy(loginsucces = true) }

                            }
                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(error = "Introduce un usuario y contraseña")
                }
            }
        }


    }

    fun doRegister() {
        viewModelScope.launch {
            if (uiState.value.username != null || uiState.value.password != null) {

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
                                            error = result.message,

                                            )
                                    }
                                }

                                is NetworkResult.Loading -> _uiState.update { it.copy() }
                                is NetworkResult.Success -> _uiState.update { it.copy(registersuccess = true) }

                            }
                        }


                    }
                }

            }


        }

    }

}