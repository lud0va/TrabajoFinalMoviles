package com.example.apolloproject.ui.screens.orders.detalle

import androidx.lifecycle.ViewModel
import com.example.apolloproject.ui.screens.orders.OrdersListaContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class OrdersViewModel():ViewModel() {
    private val _uiState: MutableStateFlow<OrdersDetalleContract.State> by lazy {
        MutableStateFlow(OrdersDetalleContract.State())
    }
    val uiState: StateFlow<OrdersDetalleContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()
}