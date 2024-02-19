package com.example.apolloproject.data.apollo.graphql.mappers


import com.apolloproject.AddCustomerMutation
import com.apolloproject.GetCustomerQuery
import com.apolloproject.GetCustomersQuery
import com.apolloproject.type.CustomerInput
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

fun GetCustomerQuery.GetCustomer.toCustomerDetail(): CustomerGraphDetail {
    return CustomerGraphDetail(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        dateOfBirth = LocalDate.parse(dateOfBirth),
        phone = phone,
    )
}

fun AddCustomerMutation.AddCustomer.toCustomerGraph(): CustomerGraph {
    return CustomerGraph(
        id=id,
        firstName = firstName,
        lastName = lastName
    )
}

fun CustomerGraphDetail.toCustomerInput(): CustomerInput {
    return CustomerInput(
        firstName = firstName,
        lastName = lastName,
        email = email,
        dateOfBirth = dateOfBirth,
        phone = phone,
    )
}