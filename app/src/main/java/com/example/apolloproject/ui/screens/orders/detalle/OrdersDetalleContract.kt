package com.example.apolloproject.ui.screens.orders.detalle

import com.example.apolloproject.domain.model.OrderGraph
import com.example.apolloproject.domain.model.OrderItemGraph

interface OrdersDetalleContract {
    sealed class Event(){

        class GetOrder(val orderId:Int):Event()
        class GetOrderItems(val orderId:Int):Event()
    }
    data class State(
        val  orderGraph: OrderGraph?=null,
        val ordeItem:List<OrderItemGraph> = emptyList(),
        val isLoading:Boolean=false,
        val  error:String?=null,

    )
}