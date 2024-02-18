package com.example.apolloproject.ui.screens.customers

import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.ui.screens.logincompose.LoginContract

interface CustomerListContract {
    sealed class Event(){
        object getCustomers:Event()
    }
    data class State(
      val  customers:List<CustomerGraph> = emptyList()

    )


}