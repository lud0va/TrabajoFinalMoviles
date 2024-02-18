package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.apollo.graphql.ApolloCustomersClient
import com.example.apolloproject.domain.graph.CustomerClient
import com.example.apolloproject.domain.model.CustomerGraph
import javax.inject.Inject

class GetCustomersUseCase @Inject constructor(private val apolloCustomersClient: ApolloCustomersClient) {
    suspend fun invoke():List<CustomerGraph> = apolloCustomersClient.getCustomers()

}