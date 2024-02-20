package com.example.apolloproject.domain.usecases.orders

import com.example.apolloproject.data.OrdersRepository
import com.example.apolloproject.data.apollo.graphql.ApolloOrdersRemoteDataSource
import com.example.apolloproject.domain.model.OrderGraph
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(val ordersRepository: OrdersRepository)  {

     fun invoke(idOrder:Int) =ordersRepository.getOrder(idOrder)
}