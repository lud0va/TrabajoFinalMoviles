package com.example.apolloproject.data

import com.example.apolloproject.data.apollo.graphql.ApolloOrdersRemoteDataSource
import com.example.apolloproject.domain.model.OrderGraph
import com.example.apolloproject.utils.NetworkResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class OrdersRepository  @Inject constructor(
    private val ordersRemoteDataSource: ApolloOrdersRemoteDataSource
)   {
    fun saveOrder(input: OrderGraph): Flow<NetworkResult<OrderGraph?>>{
        return flow {
            emit(NetworkResult.Loading())
            val  result=ordersRemoteDataSource.saveOrder(input)
            emit(result)
        }
    }
    fun getOrders(): Flow<NetworkResult<List<OrderGraph>>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=ordersRemoteDataSource.getOrders()
            emit(result)
        }
    }

    fun getOrder(idorder: Int): Flow<NetworkResult<OrderGraph?>>{
        return flow{
            emit(NetworkResult.Loading())
            val result=ordersRemoteDataSource.getOrder(idorder)
            emit(result)

        }

    }

    fun deleteOrder(idorder: Int): Flow< NetworkResult<Boolean?>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=ordersRemoteDataSource.deleteOrder(idorder)
            emit(result)
        }
    }
}