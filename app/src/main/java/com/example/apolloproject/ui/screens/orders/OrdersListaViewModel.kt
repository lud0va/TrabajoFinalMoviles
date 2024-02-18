package com.example.apolloproject.ui.screens.orders

import androidx.lifecycle.ViewModel
import com.example.apolloproject.ui.screens.customers.CustomerListContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class OrdersListaViewModel(): ViewModel() {
    private val _uiState: MutableStateFlow<OrdersListaContract.State> by lazy {
        MutableStateFlow(OrdersListaContract.State())
    }
    val uiState: StateFlow<OrdersListaContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()
}