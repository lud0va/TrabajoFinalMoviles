package com.example.apolloproject.domain.graph

import com.example.apolloproject.domain.model.OrderItemGraph

interface OrderItemsClient {
    suspend fun getOrderItemsByOrd(id:Int):List<OrderItemGraph>
    suspend fun addOrderItem(input:OrderItemGraph):OrderItemGraph?
}