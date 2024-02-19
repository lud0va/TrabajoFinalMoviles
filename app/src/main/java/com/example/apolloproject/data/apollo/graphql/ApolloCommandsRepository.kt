package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.GetCommandsByCustomerQuery
import com.example.apolloproject.data.apollo.graphql.mappers.toCommandGraphql
import com.example.apolloproject.domain.graph.CommandClient
import com.example.apolloproject.domain.graph.OrderItemsClient
import com.example.apolloproject.domain.model.CommandGraph
import javax.inject.Inject

class ApolloCommandsRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : CommandClient {
    override suspend fun getCommandsByCustomer(idCust: Int): List<CommandGraph> {
       return apolloClient
           .query(GetCommandsByCustomerQuery(idCust))
           .execute()
           .data
           ?.getCommandsByCustomer?.map { it.toCommandGraphql() }
           ?: emptyList()
    }
}