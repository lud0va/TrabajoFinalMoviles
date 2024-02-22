package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.AddOrderItemMutation
import com.apolloproject.GetOrderItemsByOrderQuery
import com.example.apolloproject.common.Constantes
import com.example.apolloproject.data.apollo.graphql.mappers.toOrderItemGraph
import com.example.apolloproject.data.apollo.graphql.mappers.toOrderItemInput
import com.example.apolloproject.domain.model.OrderItemGraph
import com.example.apolloproject.utils.NetworkResult
import javax.inject.Inject

class ApolloOrderItemsRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {



     suspend fun getOrderItemsByOrd(id: Int): NetworkResult< List<OrderItemGraph>> {
         try {
             val response =
                 apolloClient  .query(GetOrderItemsByOrderQuery(id)).execute()
             if (!response.hasErrors()) {
                 val body = response.data?.getOrderItemsByOrder?.map { it.toOrderItemGraph() }
                 body?.let {
                     return NetworkResult.Success(it)
                 }
                 error(Constantes.NO_DATA)
             } else {
                 return NetworkResult.Error(
                     response.errors?.first()?.message ?:  Constantes.SPACE
                 )

             }
         } catch (e: Exception) {
             return NetworkResult.Error(e.message ?: e.toString())
         }

    }


     suspend fun addOrderItem(input: OrderItemGraph): NetworkResult<OrderItemGraph?> {
         try {
             val response =
                 apolloClient  .mutation(AddOrderItemMutation(input.toOrderItemInput())).execute()
             if (!response.hasErrors()) {
                 val body = response.data?.addOrderItem?.toOrderItemGraph()
                 body?.let {
                     return NetworkResult.Success(it)
                 }
                 error(Constantes.NO_DATA)
             } else {
                 return NetworkResult.Error(
                     response.errors?.first()?.message ?:  Constantes.SPACE
                 )

             }
         } catch (e: Exception) {
             return NetworkResult.Error(e.message ?: e.toString())
         }


    }
}