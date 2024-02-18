package com.example.apolloproject.domain.model

import java.time.LocalDate

data class CustomerGraphDetail (
    val id:Int,
    val   firstName: String,
    val  lastName : String ,
    val  email: String ,
    val  phone:    String,
    val  dateOfBirth: LocalDate,


){

}