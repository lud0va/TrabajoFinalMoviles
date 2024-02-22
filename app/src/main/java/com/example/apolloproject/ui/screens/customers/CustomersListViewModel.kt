package com.example.apolloproject.ui.screens.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.customers.DeleteCustomerUseCase
import com.example.apolloproject.domain.usecases.customers.GetCustomersUseCase
import com.example.apolloproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel @Inject constructor(
    private val getCustomersUseCase: GetCustomersUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase,


    ) : ViewModel() {
    private val _uistate = MutableStateFlow((CustomerListContract.State()))
    val state : StateFlow<CustomerListContract.State> = _uistate.asStateFlow()

    fun event(event: CustomerListContract.Event) {
        when (event) {
            CustomerListContract.Event.getCustomers -> cargarLista()
            is CustomerListContract.Event.DeleteCustomer -> deleteCustomer(event.id)
        }
    }

    fun deleteCustomer(id: Int) {
        viewModelScope.launch {
            deleteCustomerUseCase.invoke(id)
                .collect{result->
                    when(result){
                        is NetworkResult.Error -> {
                            _uistate.update {
                                it.copy(
                                    message = result.message,

                                    )
                            }
                        }
                        is NetworkResult.Loading -> _uistate.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> cargarLista()
                    }

                }

        }


    }

    fun cargarLista() {
        viewModelScope.launch {

            getCustomersUseCase.invoke()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uistate.update {
                                it.copy(
                                    message = result.message,

                                )
                            }

                        }

                        is NetworkResult.Loading -> _uistate.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uistate.update {
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

