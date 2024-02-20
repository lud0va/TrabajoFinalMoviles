package com.example.apolloproject.domain.usecases.restauranttables

import com.example.apolloproject.data.TablesRepository
import com.example.apolloproject.data.apollo.graphql.ApolloTablesRemoteDataSource
import com.example.apolloproject.domain.model.TablesGraph
import javax.inject.Inject

class GetAllTablesByCustUseCase @Inject constructor(val tablesRepository: TablesRepository)  {

     fun invoke(idCust:Int)=tablesRepository.getAllTablesByCustomer(idCust)
}