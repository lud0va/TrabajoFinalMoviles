package com.example.apolloproject.domain.usecases.orderitems

import com.example.apolloproject.data.OrderItemRepository
import com.example.apolloproject.data.apollo.graphql.ApolloOrderItemsRemoteDataSource
import com.example.apolloproject.domain.model.OrderItemGraph
import javax.inject.Inject

class AddOrderItemUseCase @Inject constructor(val orderItemRepository: OrderItemRepository)  {

 fun invoke(input: OrderItemGraph)=orderItemRepository.addOrderItem(input)
}