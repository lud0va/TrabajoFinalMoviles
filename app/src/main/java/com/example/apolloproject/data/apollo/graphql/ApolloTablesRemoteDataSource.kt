package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.GetAllTablesByCustomerQuery
import com.example.apolloproject.data.apollo.graphql.mappers.toTableGraphql
import com.example.apolloproject.domain.model.TablesGraph
import com.example.apolloproject.utils.NetworkResult
import java.lang.Exception
import javax.inject.Inject

class ApolloTablesRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
     suspend fun getAllTablesByCustomer(idCust: Int): NetworkResult< List<TablesGraph>> {
         try {
             val response =
                 apolloClient  .query(GetAllTablesByCustomerQuery(idCust)).execute()
             if (!response.hasErrors()) {
                 val body = response.data?.getAllTablesByCustomer?.map { it.toTableGraphql() }
                 body?.let {
                     return NetworkResult.Success(it)
                 }
                 error("No data")
             } else {
                 return NetworkResult.Error(
                     response.errors?.first()?.message ?: ""
                 )

             }
         } catch (e: Exception) {
             return NetworkResult.Error(e.message ?: e.toString())
         }
    }
}