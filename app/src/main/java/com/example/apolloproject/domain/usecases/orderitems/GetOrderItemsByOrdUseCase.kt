package com.example.apolloproject.domain.usecases.orderitems

import com.example.apolloproject.data.apollo.graphql.ApolloOrderItemsRepository
import com.example.apolloproject.domain.model.OrderItemGraph
import javax.inject.Inject

class GetOrderItemsByOrdUseCase @Inject constructor(val apolloOrderItemsRepository: ApolloOrderItemsRepository)  {
suspend fun invoke(idOrder:Int):List<OrderItemGraph> =apolloOrderItemsRepository.getOrderItemsByOrd(idOrder)
}