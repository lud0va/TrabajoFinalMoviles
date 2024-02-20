package com.example.apolloproject.data

import com.example.apolloproject.data.apollo.graphql.ApolloTablesRemoteDataSource
import com.example.apolloproject.domain.model.TablesGraph
import com.example.apolloproject.utils.NetworkResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class TablesRepository  @Inject constructor(
    private val tablesRemoteDataSource: ApolloTablesRemoteDataSource
)   {
    fun getAllTablesByCustomer(idCust: Int): Flow<NetworkResult<List<TablesGraph>>>{

        return flow {
            emit(NetworkResult.Loading())
            val result=tablesRemoteDataSource.getAllTablesByCustomer(idCust)
            emit(result)
        }
    }

}