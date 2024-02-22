package com.example.apolloproject.ui.screens.customers

import com.example.apolloproject.domain.model.CustomerGraph

interface CustomerListContract {
    sealed class Event(){
        object getCustomers:Event()
        class DeleteCustomer (val id:Int):Event()
    }
    data class State(
        val  customers:List<CustomerGraph> = emptyList(),
        val  message:String?=null,
        val isLoading:Boolean=false,
    )


}