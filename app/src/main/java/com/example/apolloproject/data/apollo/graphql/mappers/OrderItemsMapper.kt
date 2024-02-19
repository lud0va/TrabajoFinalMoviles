package com.example.apolloproject.data.apollo.graphql.mappers

import com.apolloproject.AddOrderItemMutation
import com.apolloproject.GetOrderItemsByOrderQuery
import com.apolloproject.type.CustomerInput
import com.apolloproject.type.OrderItemsInput
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.domain.model.OrderItemGraph

fun AddOrderItemMutation.AddOrderItem.toOrderItemGraph(): OrderItemGraph {
    return OrderItemGraph(
        orderItemId = orderItemId,
        name = name,
        price = price,
        quantity = quantity,
        orderId = 0

    )
}

fun OrderItemGraph.toOrderItemInput(): OrderItemsInput {
    return OrderItemsInput(
        name = name,
        price = price,
        quantity = quantity,
        orderId = orderId,

    )
}

fun GetOrderItemsByOrderQuery.GetOrderItemsByOrder.toOrderItemGraph(): OrderItemGraph {
    return OrderItemGraph(
        orderItemId = orderItemId,
        name = name,
        price = price,
        quantity = quantity,
        orderId = 0

    )
}