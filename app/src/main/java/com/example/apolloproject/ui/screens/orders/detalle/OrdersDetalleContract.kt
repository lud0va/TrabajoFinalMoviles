package com.example.apolloproject.ui.screens.orders.detalle

import com.example.apolloproject.domain.model.OrderGraph
import com.example.apolloproject.domain.model.OrderItemGraph

interface OrdersDetalleContract {
    sealed class Event(){
        object AddordeItem:Event()
        class GetOrder(val orderId:Int):Event()
        class GetOrderItems(val orderId:Int):Event()
    }
    data class State(
        val  orderGraph: OrderGraph?=null,
        val ordeItem:List<OrderItemGraph> = emptyList(),
        val orderItemAdd:OrderItemGraph=OrderItemGraph(orderItemId = null,"",2.2,1,2),
        val isLoading:Boolean=false,
        val  message:String?=null,

        )
}