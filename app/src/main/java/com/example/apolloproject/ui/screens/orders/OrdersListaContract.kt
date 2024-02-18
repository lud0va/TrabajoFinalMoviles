package com.example.apolloproject.ui.screens.orders

import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.OrderGraph

interface OrdersListaContract {
    sealed class Event(){
        object getOrder:Event()
    }
    data class State(
        val  orders:List<OrderGraph> = emptyList()

    )
}