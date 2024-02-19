package com.example.apolloproject.domain.graph

import com.example.apolloproject.domain.model.OrderGraph

interface OrderClient {

    suspend fun getOrders():List<OrderGraph>
    suspend fun getOrder(idorder:Int):OrderGraph?

    suspend fun deleteOrder(idorder: Int):Boolean?
}