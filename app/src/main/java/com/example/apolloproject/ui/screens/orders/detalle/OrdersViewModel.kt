package com.example.apolloproject.ui.screens.orders.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.orderitems.GetOrderItemsByOrdUseCase
import com.example.apolloproject.domain.usecases.orders.GetOrderUseCase
import com.example.apolloproject.ui.screens.customers.detalle.CustomerDetalleContract
import com.example.apolloproject.ui.screens.orders.OrdersListaContract
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
class OrdersViewModel @Inject constructor(
    val getOrderUseCase: GetOrderUseCase,
    val getOrderItemsByOrdUseCase: GetOrderItemsByOrdUseCase

):ViewModel() {
    private val _uiState: MutableStateFlow<OrdersDetalleContract.State> by lazy {
        MutableStateFlow(OrdersDetalleContract.State())
    }
    val uiState: StateFlow<OrdersDetalleContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun event(event: OrdersDetalleContract.Event){
        when(event){
            is OrdersDetalleContract.Event.GetOrder ->getOrder(event.orderId)
            is OrdersDetalleContract.Event.GetOrderItems -> getOrderItems(event.orderId)
        }


    }

    fun getOrder(id:Int){
        viewModelScope.launch {

                 getOrderUseCase.invoke(id)
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
                                     orderGraph = result.data
                                        , isLoading = false
                                 )
                             }
                         }

                     }


        }

    }
    fun getOrderItems(id:Int){
        viewModelScope.launch {

              getOrderItemsByOrdUseCase.invoke(id)
                  .collect{
                          result->
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
                                  ordeItem = result.data ?: emptyList()
                                  , isLoading = false
                              )
                          }
                      }
                  }

        }

    }
}