package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.CustomersRepository
import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRemoteDataSource
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import javax.inject.Inject

class AddCustomerUseCase @Inject constructor( val customersRepository: CustomersRepository)  {
 fun invoke(input: CustomerGraphDetail)=customersRepository.addCustomer(input)
}