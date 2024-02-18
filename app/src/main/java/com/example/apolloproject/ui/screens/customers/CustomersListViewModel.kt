package com.example.apolloproject.ui.screens.customers

import androidx.lifecycle.ViewModel
import com.example.apolloproject.ui.screens.logincompose.LoginContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel  @Inject constructor(


):ViewModel(){
    private val _uiState: MutableStateFlow<CustomerListContract.State> by lazy {
        MutableStateFlow(CustomerListContract.State())
    }
    val uiState: StateFlow<CustomerListContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()




}