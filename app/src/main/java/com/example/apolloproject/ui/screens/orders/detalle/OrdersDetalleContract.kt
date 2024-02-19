package com.example.apolloproject.ui.screens.orders.detalle

import com.example.apolloproject.domain.model.CommandGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.domain.model.OrderGraph
import com.example.apolloproject.domain.model.OrderItemGraph
import com.example.apolloproject.domain.model.TablesGraph

interface OrdersDetalleContract {
    sealed class Event(){

        class GetOrder(val orderId:Int):Event()
        class GetOrderItems(val orderId:Int):Event()
    }
    data class State(
        val  orderGraph: OrderGraph?=null,
        val ordeItem:List<OrderItemGraph> = emptyList(),


    )
}