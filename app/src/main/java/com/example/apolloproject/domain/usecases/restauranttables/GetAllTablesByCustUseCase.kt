package com.example.apolloproject.domain.usecases.restauranttables

import com.example.apolloproject.data.apollo.graphql.ApolloTablesRepository
import com.example.apolloproject.domain.model.TablesGraph
import javax.inject.Inject

class GetAllTablesByCustUseCase @Inject constructor(val apolloTablesRepository: ApolloTablesRepository)  {

    suspend fun invoke(idCust:Int):List<TablesGraph> =apolloTablesRepository.getAllTablesByCustomer(idCust)
}