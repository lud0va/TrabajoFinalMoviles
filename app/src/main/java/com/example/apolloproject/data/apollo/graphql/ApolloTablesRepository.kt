package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.GetAllTablesByCustomerQuery
import com.example.apolloproject.data.apollo.graphql.mappers.toTableGraphql
import com.example.apolloproject.domain.graph.OrderItemsClient
import com.example.apolloproject.domain.graph.TablesClient
import com.example.apolloproject.domain.model.TablesGraph
import javax.inject.Inject

class ApolloTablesRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : TablesClient {
    override suspend fun getAllTablesByCustomer(idCust: Int): List<TablesGraph> {
       return apolloClient
           .query(GetAllTablesByCustomerQuery(idCust))
           .execute()
           .data
           ?.getAllTablesByCustomer?.map { it.toTableGraphql() }
           ?: emptyList()
    }
}