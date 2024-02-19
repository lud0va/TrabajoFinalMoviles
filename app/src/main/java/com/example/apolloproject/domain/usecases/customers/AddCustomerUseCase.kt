package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRepository
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import javax.inject.Inject

class AddCustomerUseCase @Inject constructor(private val apolloCustomersRepository: ApolloCustomersRepository)  {
suspend fun invoke(input: CustomerGraphDetail): CustomerGraph?=apolloCustomersRepository.addCustomer(input)
}