package com.example.apolloproject.data

import com.example.apolloproject.data.apollo.graphql.ApolloOrderItemsRemoteDataSource
import com.example.apolloproject.domain.model.OrderItemGraph
import com.example.apolloproject.utils.NetworkResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class OrderItemRepository  @Inject constructor(
    private val orderItemsRemoteDataSource: ApolloOrderItemsRemoteDataSource
)   {
    fun getOrderItemsByOrd(id: Int): Flow<NetworkResult<List<OrderItemGraph>>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=orderItemsRemoteDataSource.getOrderItemsByOrd(id)
            emit(result)
        }
    }

    fun addOrderItem(input: OrderItemGraph): Flow<NetworkResult<OrderItemGraph?>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=orderItemsRemoteDataSource.addOrderItem(input)
            emit(result)
        }
    }
}