package com.example.apolloproject.ui.screens.orders.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.R
import com.example.apolloproject.domain.usecases.orderitems.AddOrderItemUseCase
import com.example.apolloproject.domain.usecases.orderitems.GetOrderItemsByOrdUseCase
import com.example.apolloproject.domain.usecases.orders.GetOrderUseCase
import com.example.apolloproject.utils.NetworkResult
import com.example.apolloproject.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private  val stringProvider: StringProvider,
    val getOrderUseCase: GetOrderUseCase,
    val getOrderItemsByOrdUseCase: GetOrderItemsByOrdUseCase,
    val addOrderItemUseCase: AddOrderItemUseCase

):ViewModel() {
    private val _uiState= MutableStateFlow(OrdersDetalleContract.State())
    val uiState: StateFlow<OrdersDetalleContract.State> = _uiState.asStateFlow()


    fun event(event: OrdersDetalleContract.Event){
        when(event){
            is OrdersDetalleContract.Event.GetOrder ->getOrder(event.orderId)
            is OrdersDetalleContract.Event.GetOrderItems -> getOrderItems(event.orderId)
            OrdersDetalleContract.Event.AddordeItem -> addOrderItem()
        }


    }

    fun addOrderItem(){
        viewModelScope.launch {
                addOrderItemUseCase.invoke(uiState.value.orderItemAdd)
                    .collect{result->
                        when(result){
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = result.message,

                                        )
                                }
                            }

                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy( message = stringProvider.getString(R.string.orderitemadded)
                                )
                            }
                        }

                    }



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
                                         message = result.message,

                                         )
                                 }
                             }

                             is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                             is NetworkResult.Success -> _uiState.update {
                                 it.copy( orderItemAdd = it.orderItemAdd.copy(orderId = result.data?.orderId?:1, name = stringProvider.getString(R.string.item)+ (result.data?.orderId?:1)+stringProvider.getString(R.string.bb)+uiState.value.ordeItem.size, price = 2.1, quantity =result.data?.orderId?:1 ),
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
                                      message = result.message,

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