package com.example.apolloproject.data.apollo.graphql.mappers

import com.apolloproject.GetCommandsByCustomerQuery
import com.example.apolloproject.domain.model.CommandGraph

fun GetCommandsByCustomerQuery.GetCommandsByCustomer.toCommandGraphql():CommandGraph{
    return CommandGraph(
        id=id,
        commandName=commandName,
    )
}