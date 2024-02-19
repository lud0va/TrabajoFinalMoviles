package com.example.apolloproject.ui.screens.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.customers.GetCustomersUseCase
import com.example.apolloproject.ui.screens.logincompose.LoginContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel @Inject constructor(
    private val getCust: GetCustomersUseCase

) : ViewModel() {
    private val _uiState: MutableStateFlow<CustomerListContract.State> by lazy {
        MutableStateFlow(CustomerListContract.State())
    }
    val uiState: StateFlow<CustomerListContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun event(event: CustomerListContract.Event) {
        when (event) {
            CustomerListContract.Event.getCustomers -> cargarLista()
        }
    }

    fun cargarLista() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(customers = getCust.invoke())
            }
        }
    }

}