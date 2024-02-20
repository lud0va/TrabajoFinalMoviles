package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.CustomersRepository
import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRemoteDataSource
import com.example.apolloproject.domain.model.CustomerGraph
import javax.inject.Inject

class GetCustomersUseCase @Inject constructor(val customersRepository: CustomersRepository) {
     fun invoke()= customersRepository.getCustomers()

}