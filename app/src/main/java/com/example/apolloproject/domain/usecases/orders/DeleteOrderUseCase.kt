package com.example.apolloproject.domain.usecases.orders

import com.example.apolloproject.data.OrdersRepository
import com.example.apolloproject.data.apollo.graphql.ApolloOrdersRemoteDataSource
import javax.inject.Inject

class DeleteOrderUseCase @Inject constructor(val ordersRepository: OrdersRepository)  {

     fun invoke(idOrder:Int)=ordersRepository.deleteOrder(idOrder)

}