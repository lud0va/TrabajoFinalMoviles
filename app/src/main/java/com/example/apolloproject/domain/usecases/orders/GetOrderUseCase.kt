package com.example.apolloproject.domain.usecases.orders

import com.example.apolloproject.data.apollo.graphql.ApolloOrdersRepository
import com.example.apolloproject.domain.model.OrderGraph
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(val ordersRepository: ApolloOrdersRepository)  {

    suspend fun invoke(idOrder:Int):OrderGraph? =ordersRepository.getOrder(idOrder)
}