package com.example.apolloproject.ui.screens.customers.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.commands.GetCommandsByCustomerUseCase
import com.example.apolloproject.domain.usecases.customers.GetCustomerUseCase
import com.example.apolloproject.domain.usecases.restauranttables.GetAllTablesByCustUseCase
import com.example.apolloproject.ui.screens.customers.CustomerListContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CustomerDetalleViewModel @Inject constructor(
   val getCustomerUseCase: GetCustomerUseCase,
   val getAllTablesByCustUseCase: GetAllTablesByCustUseCase,
    val getAllCommandUseCase: GetCommandsByCustomerUseCase



) :ViewModel(){
    private val _uiState: MutableStateFlow<CustomerDetalleContract.State> by lazy {
        MutableStateFlow(CustomerDetalleContract.State())
    }
    val uiState: StateFlow<CustomerDetalleContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun event(event:CustomerDetalleContract.Event){
        when(event){
            is CustomerDetalleContract.Event.GetCustomer -> getCustomer(event.customerId)
            is CustomerDetalleContract.Event.GetTables ->getTables(event.customerId)
        }
    }


    fun getTables(idCust:Int){
        viewModelScope.launch {
            _uiState.update {
                it.copy(tables = getAllTablesByCustUseCase.invoke(idCust))
            }
        }
    }
    fun getCustomer(idCust:Int){
        viewModelScope.launch {
            _uiState.update {
                it.copy(customer = getCustomerUseCase.invoke(idCust))
            }
        }
    }
}