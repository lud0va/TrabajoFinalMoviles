package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRepository
import com.example.apolloproject.domain.model.CustomerGraph
import javax.inject.Inject

class GetCustomersUseCase @Inject constructor(private val apolloCustomersRepository: ApolloCustomersRepository) {
    suspend fun invoke():List<CustomerGraph> = apolloCustomersRepository.getCustomers()

}