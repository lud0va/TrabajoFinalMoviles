package com.example.apolloproject.ui.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.domain.usecases.orders.DeleteOrderUseCase
import com.example.apolloproject.domain.usecases.orders.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrdersListaViewModel @Inject constructor(
    val getOrdersUseCase: GetOrdersUseCase,
    val deleteOrderUseCase: DeleteOrderUseCase

): ViewModel() {
    private val _uiState: MutableStateFlow<OrdersListaContract.State> by lazy {
        MutableStateFlow(OrdersListaContract.State())
    }
    val uiState: StateFlow<OrdersListaContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()


    fun event(event: OrdersListaContract.Event) {
        when (event) {
            OrdersListaContract.Event.GetOrders -> getListaOrder()
            is OrdersListaContract.Event.DeleteOrder -> deleteOrder(event.idOrder)
        }
    }

    fun deleteOrder(id:Int){

    }
    fun getListaOrder(){
        viewModelScope.launch {

            _uiState.update {
                it.copy(orders = getOrdersUseCase.invoke())
            }
        }

    }


}