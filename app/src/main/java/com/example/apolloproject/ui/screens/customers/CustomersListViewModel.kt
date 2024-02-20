package com.example.apolloproject.ui.screens.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.customers.AddCustomerUseCase
import com.example.apolloproject.domain.usecases.customers.DeleteCustomerUseCase
import com.example.apolloproject.domain.usecases.customers.GetCustomersUseCase
import com.example.apolloproject.ui.screens.logincompose.LoginContract
import com.example.apolloproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel @Inject constructor(
    private val getCust: GetCustomersUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
    private val addCustomerUseCase: AddCustomerUseCase

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
            is CustomerListContract.Event.deleteCustomer -> deleteCustomer(event.id)
        }
    }

    fun deleteCustomer(id: Int) {
        viewModelScope.launch {
            deleteCustomerUseCase.invoke(id)
                .collect{result->
                    when(result){
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message,

                                    )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> cargarLista()
                    }

                }

        }


    }

    fun cargarLista() {
        viewModelScope.launch {

            getCust.invoke()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message,

                                )
                            }
                            //_uiError.send(result.message ?: "Error")
                        }

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                customers = result.data
                                    ?: emptyList(), isLoading = false
                            )
                        }

                    }


                }

        }
    }
}

