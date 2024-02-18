package com.example.apolloproject.data.apollo.graphql.mappers

import com.apolloproject.GetCustomerQuery
import com.apolloproject.GetCustomersQuery
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import java.time.LocalDate

fun GetCustomersQuery.GetCustomer.toCustomerGraph(): CustomerGraph {
   return CustomerGraph(
        id = id,
        firstName = firstName,
        lastName = lastName
    )

}
fun GetCustomerQuery.GetCustomer.toCustomerDetail():CustomerGraphDetail{
    return CustomerGraphDetail(
        id = id,
        firstName=firstName,
        lastName=lastName,
        email=email,
        dateOfBirth= LocalDate.parse(dateOfBirth),
        phone = phone,
    )
}
