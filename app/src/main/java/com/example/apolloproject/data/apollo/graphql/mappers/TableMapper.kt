package com.example.apolloproject.data.apollo.graphql.mappers

import com.apolloproject.GetAllTablesByCustomerQuery
import com.example.apolloproject.domain.model.TablesGraph

fun GetAllTablesByCustomerQuery.GetAllTablesByCustomer.toTableGraphql():TablesGraph{
    return TablesGraph(
        id=id,
        numberOfSeats=numberOfSeats,
    )
}