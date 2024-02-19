package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.DeleteOrderMutation
import com.apolloproject.GetAllOrdersQuery
import com.apolloproject.GetOrderQuery
import com.example.apolloproject.data.apollo.graphql.mappers.toOrder
import com.example.apolloproject.domain.graph.OrderClient
import com.example.apolloproject.domain.model.OrderGraph
import javax.inject.Inject

class ApolloOrdersRepository @Inject constructor(
    private val apolloClient: ApolloClient
) :OrderClient {
    override suspend fun getOrders(): List<OrderGraph> {
    return apolloClient
        .query(GetAllOrdersQuery())
        .execute()
        .data
        ?.getAllOrders
        ?.map { it.toOrder() }
        ?: emptyList()

    }

    override suspend fun getOrder(idorder: Int): OrderGraph? {
        return apolloClient
            .query(GetOrderQuery(idorder))
            .execute()
            .data
            ?.getOrder?.toOrder()
    }

    override suspend fun deleteOrder(idorder: Int): Boolean? {
        return apolloClient
            .mutation(DeleteOrderMutation(idorder))
            .execute()
            .data?.deleteOrder


    }
}