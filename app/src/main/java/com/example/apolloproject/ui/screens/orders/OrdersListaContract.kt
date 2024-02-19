package com.example.apolloproject.ui.screens.orders

import com.example.apolloproject.domain.model.OrderGraph

interface OrdersListaContract {
    sealed class Event(){
        class DeleteOrder(val idOrder:Int):Event()
        object GetOrders:Event()
    }
    data class State(
        val  orders:List<OrderGraph> = emptyList()

    )
}