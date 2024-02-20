package com.example.apolloproject.data.apollo.graphql.mappers


import com.apollographql.apollo3.api.Optional
import com.apolloproject.AddCustomerMutation
import com.apolloproject.GetCustomerQuery
import com.apolloproject.GetCustomersQuery
import com.apolloproject.UpdateCustomerMutation
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

fun UpdateCustomerMutation.UpdateCustomer.toCustomerUpdate():CustomerGraphDetail{
    return CustomerGraphDetail(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        dateOfBirth = LocalDate.parse(dateOfBirth),
        phone = phone,
    )

}
fun CustomerGraphDetail.toCustomerInputConId():CustomerInput{
    return CustomerInput(
        id= Optional.present(id),
        firstName = firstName,
        lastName = lastName,
        email = email,
        dateOfBirth = dateOfBirth,
        phone = phone,
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