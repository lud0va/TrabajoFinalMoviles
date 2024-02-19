package com.example.apolloproject.domain.usecases.commands

import com.example.apolloproject.data.apollo.graphql.ApolloCommandsRepository
import com.example.apolloproject.domain.model.CommandGraph
import javax.inject.Inject

class GetCommandsByCustomerUseCase @Inject constructor(val apolloCommandsRepository: ApolloCommandsRepository) {

    suspend fun invoke(idCust:Int):List<CommandGraph> = apolloCommandsRepository.getCommandsByCustomer(idCust)
}