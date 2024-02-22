package com.example.apolloproject.domain.usecases.orders

import com.example.apolloproject.data.OrdersRepository
import com.example.apolloproject.domain.model.OrderGraph
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(val ordersRepository: OrdersRepository)  {
    fun invoke(order:OrderGraph)=ordersRepository.saveOrder(order)

}