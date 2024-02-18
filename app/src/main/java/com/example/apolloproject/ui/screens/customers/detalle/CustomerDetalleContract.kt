package com.example.apolloproject.ui.screens.customers.detalle

import com.example.apolloproject.domain.model.CommandGraph
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.domain.model.TablesGraph

interface CustomerDetalleContract {

    sealed class Event(){
        class GetCustomer(val customerId:Int):Event()
        class GetTables(val customerId:Int):Event()
        class GetCommands(val customerId:Int):Event()
    }
    data class State(
        val  customer:CustomerGraphDetail?=null,
        val tables:List<TablesGraph> = emptyList(),
        val commands:List<CommandGraph> = emptyList()


    )
}