package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.CustomersRepository
import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRemoteDataSource
import com.example.apolloproject.domain.model.CustomerGraphDetail
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(val customersRepository: CustomersRepository)  {
     fun invoke(id:Int) = customersRepository.getCustomer(id)


}