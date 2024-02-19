package com.example.apolloproject.data.apollo.graphql.mappers

import com.apolloproject.GetAllOrdersQuery
import com.apolloproject.GetOrderQuery
import com.example.apolloproject.domain.model.OrderGraph
import java.time.LocalDateTime

fun GetAllOrdersQuery.GetAllOrder.toOrder():OrderGraph {
    return OrderGraph(
        orderId=orderId,
        orderDate= LocalDateTime.parse(orderDate),

    )


}
fun GetOrderQuery.GetOrder.toOrder():OrderGraph{
    return OrderGraph(
        orderId=orderId,
        orderDate= LocalDateTime.parse(orderDate),

        )
}