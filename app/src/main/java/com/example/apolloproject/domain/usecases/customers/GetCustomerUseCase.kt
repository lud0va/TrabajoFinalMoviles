package com.example.apolloproject.domain.usecases.customers

import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRepository
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(private val apolloCustomersRepository: ApolloCustomersRepository)  {
    suspend fun invoke(id:Int):CustomerGraphDetail? = apolloCustomersRepository.getCustomer(id)


}