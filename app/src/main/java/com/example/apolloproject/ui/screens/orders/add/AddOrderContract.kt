package com.example.apolloproject.ui.screens.orders.add

import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.OrderGraph
import com.example.apolloproject.domain.model.OrderItemGraph
import java.time.LocalDateTime

interface AddOrderContract {
    sealed class Event(){
        class CambiarCustomerId(val id:String): Event()

        object Addorder:Event()
        object GetCustomers:Event()
    }
    data class State(
        val  orderGraph: OrderGraph?= OrderGraph(orderId = null, LocalDateTime.now(),customerId = 0),
        val  customers:List<CustomerGraph> = emptyList(),
        val  message:String?=null,
        val isLoading:Boolean=false,

        )
}