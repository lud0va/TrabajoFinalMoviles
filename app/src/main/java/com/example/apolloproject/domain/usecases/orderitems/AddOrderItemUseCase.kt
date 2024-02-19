package com.example.apolloproject.domain.usecases.orderitems

import com.example.apolloproject.data.apollo.graphql.ApolloOrderItemsRepository
import com.example.apolloproject.domain.model.OrderItemGraph
import javax.inject.Inject

class AddOrderItemUseCase @Inject constructor(val apolloOrderItemsRepository: ApolloOrderItemsRepository)  {

suspend fun invoke(input: OrderItemGraph): OrderItemGraph?=apolloOrderItemsRepository.addOrderItem(input)
}