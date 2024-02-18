package com.example.apolloproject.ui.screens.customers.detalle

import androidx.lifecycle.ViewModel
import com.example.apolloproject.ui.screens.customers.CustomerListContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class CustomerDetalleViewModel @Inject constructor(



) :ViewModel(){
    private val _uiState: MutableStateFlow<CustomerDetalleContract.State> by lazy {
        MutableStateFlow(CustomerDetalleContract.State())
    }
    val uiState: StateFlow<CustomerDetalleContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()
}