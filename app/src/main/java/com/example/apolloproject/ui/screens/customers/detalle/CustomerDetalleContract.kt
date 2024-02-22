package com.example.apolloproject.ui.screens.customers.detalle

import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.domain.model.TablesGraph

interface CustomerDetalleContract {

    sealed class Event(){
        class UpdateCustomer(val customer: CustomerGraphDetail?):Event()
        class GetCustomer(val customerId:Int):Event()
        class GetTables(val customerId:Int):Event()

        class CambiarFirstName(val first:String):Event()
        class CambiarLastName(val last:String):Event()
        class CambiarPhone(val phone:String):Event()
        class CambiarEmail(val email:String):Event()

    }
    data class State(
        val  customer:CustomerGraphDetail?=null,
        val tables:List<TablesGraph> = emptyList(),
        val  message:String?=null,

        val isLoading:Boolean=false,

        )
}