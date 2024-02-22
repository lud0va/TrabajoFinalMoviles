package com.example.apolloproject.ui.screens.orders

import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.OrderGraph

interface OrdersListaContract {
    sealed class Event(){
        class DeleteOrder(val idOrder:Int):Event()
        object GetOrders:Event()
    }
    data class State(

        val order:OrderGraph?=null,
        val  orders:List<OrderGraph> = emptyList(),
        val isLoading:Boolean=false,
        val  message:String?=null,

        )
}