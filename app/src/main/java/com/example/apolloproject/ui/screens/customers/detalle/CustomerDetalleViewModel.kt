package com.example.apolloproject.ui.screens.customers.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.customers.GetCustomerUseCase
import com.example.apolloproject.domain.usecases.restauranttables.GetAllTablesByCustUseCase
import com.example.apolloproject.ui.screens.customers.CustomerListContract
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
class CustomerDetalleViewModel @Inject constructor(
   val getCustomerUseCase: GetCustomerUseCase,
   val getAllTablesByCustUseCase: GetAllTablesByCustUseCase,



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

              getAllTablesByCustUseCase.invoke(idCust)
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
                          is NetworkResult.Success -> _uiState.update {
                              it.copy(
                                  tables = result.data
                                      ?: emptyList(), isLoading = false
                              )
                          }
                      }

                  }

        }
    }
    fun getCustomer(idCust:Int){
        viewModelScope.launch {

               getCustomerUseCase.invoke(idCust)
                   .collect{result->
                       when(result){
                           is NetworkResult.Error ->  {
                           _uiState.update {
                               it.copy(
                                   error = result.message,

                                   )
                           }
                       }
                           is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                           is NetworkResult.Success -> _uiState.update {
                               it.copy(
                                   customer = result.data
                                      , isLoading = false
                               )
                           }
                       }
                   }

        }
    }
}