package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.DeleteOrderMutation
import com.apolloproject.GetAllOrdersQuery
import com.apolloproject.GetOrderQuery
import com.apolloproject.SaveOrderMutation
import com.example.apolloproject.data.apollo.graphql.mappers.toOrder
import com.example.apolloproject.data.apollo.graphql.mappers.toOrderGrapg
import com.example.apolloproject.data.apollo.graphql.mappers.toOrderInput
import com.example.apolloproject.domain.model.OrderGraph
import com.example.apolloproject.utils.NetworkResult
import javax.inject.Inject

class ApolloOrdersRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {


    suspend fun saveOrder(input: OrderGraph): NetworkResult<OrderGraph?> {
        try {
            val response =
                apolloClient.mutation(SaveOrderMutation(input.toOrderInput())).execute()
            if (!response.hasErrors()) {
                val body = response.data?.saveOrder?.toOrderGrapg()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error("No data")
            } else {
                return NetworkResult.Error(
                    response.errors?.first()?.message ?: ""
                )

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }

    }


    suspend fun getOrders(): NetworkResult<List<OrderGraph>>  {
        try {
            val response =
                apolloClient.query(GetAllOrdersQuery()).execute()
            if (!response.hasErrors()) {
                val body = response.data?.getAllOrders?.map { it.toOrder() }
                body?.let {
                    return NetworkResult.Success(it)
                }
                error("No data")
            } else {
                return NetworkResult.Error(
                    response.errors?.first()?.message ?: ""
                )

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }

    }

    suspend fun getOrder(idorder: Int): NetworkResult<OrderGraph?>  {
        try {
            val response =
                apolloClient .query(GetOrderQuery(idorder)).execute()
            if (!response.hasErrors()) {
                val body = response.data?.getOrder?.toOrder()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error("No data")
            } else {
                return NetworkResult.Error(
                    response.errors?.first()?.message ?: ""
                )

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun deleteOrder(idorder: Int):  NetworkResult<Boolean?>  {
        try {
            val response =
                apolloClient.mutation(DeleteOrderMutation(idorder)).execute()
            if (!response.hasErrors()) {
                val body = response.data?.deleteOrder
                body?.let {
                    return NetworkResult.Success(it)
                }
                error("No data")
            } else {
                return NetworkResult.Error(
                    response.errors?.first()?.message ?: ""
                )

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }


    }
}