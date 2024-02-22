package com.example.apolloproject.data.apollo.graphql.mappers

import com.apollographql.apollo3.api.Optional
import com.apolloproject.GetAllOrdersQuery
import com.apolloproject.GetOrderQuery
import com.apolloproject.SaveOrderMutation
import com.apolloproject.type.OrderInput
import com.example.apolloproject.domain.model.OrderGraph
import java.time.LocalDateTime

fun GetAllOrdersQuery.GetAllOrder.toOrder(): OrderGraph {
    return OrderGraph(
        orderId = orderId,
        orderDate = LocalDateTime.parse(orderDate),
        customerId = customer?.id


    )


}

fun GetOrderQuery.GetOrder.toOrder(): OrderGraph {
    return OrderGraph(
        orderId = orderId,
        orderDate = LocalDateTime.parse(orderDate),
        customerId = null


    )
}

fun OrderGraph.toOrderInput():OrderInput{
    return OrderInput(
        orderId= Optional.present(orderId),
        orderDate=orderDate.toString(),
        customerid= Optional.present(customerId)
    )
}
fun SaveOrderMutation.SaveOrder.toOrderGrapg(): OrderGraph {
    return OrderGraph(
        orderId = null,
        orderDate = LocalDateTime.parse(orderDate),
        customerId =null
    )
}