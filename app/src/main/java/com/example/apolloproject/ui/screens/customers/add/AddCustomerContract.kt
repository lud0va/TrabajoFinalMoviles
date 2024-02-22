package com.example.apolloproject.ui.screens.customers.add

import com.example.apolloproject.domain.model.CustomerGraphDetail
import java.time.LocalDate


interface AddCustomerContract {

    sealed class Event(){
        class AddCustomer (val customer: CustomerGraphDetail?):Event()
        class CambiarFirstName(val first:String): Event()
        class CambiarLastName(val last:String): Event()
        class CambiarPhone(val phone:String): Event()
        class CambiarEmail(val email:String):Event()
    }
    data class State(
        val  customer: CustomerGraphDetail?=CustomerGraphDetail(id = null, firstName = "", lastName = "", email = "", phone = "",
            LocalDate.now()),
        val  message:String?=null,
        val isLoading:Boolean=false,
    )
}