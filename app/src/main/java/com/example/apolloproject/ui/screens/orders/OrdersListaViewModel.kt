package com.example.apolloproject.ui.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.model.OrderGraph
import com.example.apolloproject.domain.usecases.orders.DeleteOrderUseCase
import com.example.apolloproject.domain.usecases.orders.GetOrdersUseCase
import com.example.apolloproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OrdersListaViewModel @Inject constructor(
    val getOrdersUseCase: GetOrdersUseCase,
    val deleteOrderUseCase: DeleteOrderUseCase,


) : ViewModel() {
    private val _uiState = MutableStateFlow(OrdersListaContract.State())
    val state: StateFlow<OrdersListaContract.State> = _uiState.asStateFlow()

    val uiState: StateFlow<OrdersListaContract.State> = _uiState


    fun event(event: OrdersListaContract.Event) {
        when (event) {
            OrdersListaContract.Event.GetOrders -> getListaOrder()
            is OrdersListaContract.Event.DeleteOrder -> deleteOrder(event.idOrder)
        }
    }






    fun deleteOrder(id: Int) {
        viewModelScope.launch {
            deleteOrderUseCase.invoke(id)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    message = result.message,

                                    )
                            }
                        }

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> {
                            getListaOrder()

                        }


                    }
                }

        }
    }


    fun getListaOrder() {

        viewModelScope.launch {

            getOrdersUseCase.invoke()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    message = result.message,

                                    )
                            }
                        }

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(order = OrderGraph(orderId = null, LocalDateTime.now(),customerId = result.data?.get(0)?.customerId),
                                    orders = result.data ?: emptyList(), isLoading = false
                                )
                            }

                        }
                    }

                }

        }
    }

}