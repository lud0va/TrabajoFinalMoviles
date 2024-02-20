package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.CustomersRepository
import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRemoteDataSource
import javax.inject.Inject

class DeleteCustomerUseCase @Inject constructor(val customersRepository: CustomersRepository) {
     fun invoke(id:Int)=customersRepository.deleteCustomer(id)

}