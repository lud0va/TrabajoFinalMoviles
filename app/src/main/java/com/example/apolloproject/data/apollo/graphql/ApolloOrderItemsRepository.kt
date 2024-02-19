package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.AddOrderItemMutation
import com.apolloproject.GetOrderItemsByOrderQuery
import com.example.apolloproject.data.apollo.graphql.mappers.toOrderItemGraph
import com.example.apolloproject.data.apollo.graphql.mappers.toOrderItemInput
import com.example.apolloproject.domain.graph.OrderItemsClient
import com.example.apolloproject.domain.model.OrderItemGraph
import javax.inject.Inject

class ApolloOrderItemsRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : OrderItemsClient {
    override suspend fun getOrderItemsByOrd(id: Int): List<OrderItemGraph> {
        return apolloClient
            .query(GetOrderItemsByOrderQuery(id))
            .execute()
            .data
            ?.getOrderItemsByOrder
            ?.map { it.toOrderItemGraph() }
            ?:emptyList()

    }

    override suspend fun addOrderItem(input: OrderItemGraph): OrderItemGraph? {
        return apolloClient
            .mutation(AddOrderItemMutation(input.toOrderItemInput()))
            .execute()
            .data?.addOrderItem?.toOrderItemGraph()


    }
}