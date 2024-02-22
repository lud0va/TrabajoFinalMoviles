package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.AddCustomerMutation
import com.apolloproject.DeleteCustomerMutation
import com.apolloproject.DeleteOrderMutation
import com.apolloproject.GetCustomerQuery
import com.apolloproject.GetCustomersQuery
import com.apolloproject.UpdateCustomerMutation
import com.example.apolloproject.common.Constantes
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerDetail
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerGraph
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerInput
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerInputConId
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerUpdate
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.utils.NetworkResult
import com.example.apolloproject.utils.NetworkResult.Error
import java.lang.Exception
import javax.inject.Inject



class ApolloCustomersRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun updateCustomer(input: CustomerGraphDetail): NetworkResult<CustomerGraphDetail?> {
        try {
            val response =
                apolloClient.mutation(UpdateCustomerMutation(input.toCustomerInputConId())).execute()
            if (!response.hasErrors()) {
                val body = response.data?.updateCustomer?.toCustomerUpdate()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
              return Error(
                  response.errors?.first()?.message ?:Constantes.SPACE
              )

            }
        } catch (e: Exception) {
            return Error(e.message ?: e.toString())
        }

    }

    suspend fun deleteCustomer(idCustomer: Int): NetworkResult<Boolean?> {
        try {
            val response =
                apolloClient.mutation(DeleteCustomerMutation(idCustomer)).execute()
            if (!response.hasErrors()) {
                val body = response.data?.deleteCustomer
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
                return Error(
                    response.errors?.first()?.message ?: Constantes.SPACE
                )

            }
        } catch (e: Exception) {
            return Error(e.message ?: e.toString())
        }

    }

    suspend fun getCustomer(id: Int): NetworkResult<CustomerGraphDetail?> {
        try {
            val response =
                apolloClient .query(GetCustomerQuery(id)).execute()
            if (!response.hasErrors()) {
                val body = response.data?.getCustomer?.toCustomerDetail()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
                return Error(
                    response.errors?.first()?.message ?: Constantes.SPACE
                )

            }
        } catch (e: Exception) {
            return Error(e.message ?: e.toString())
        }

    }

    suspend fun getCustomers(): NetworkResult<List<CustomerGraph>> {
        try {
            val response =
                apolloClient .query(GetCustomersQuery()).execute()
            if (!response.hasErrors()) {
                val body = response.data?.getCustomers?.map { it.toCustomerGraph() }
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
                return Error(
                    response.errors?.first()?.message ?: Constantes.SPACE
                )

            }
        } catch (e: Exception) {
            return Error(e.message ?: e.toString())
        }


    }



    suspend fun addCustomer(input: CustomerGraphDetail): NetworkResult<CustomerGraphDetail?> {
        try {
            val response =
                apolloClient  .mutation(AddCustomerMutation(input.toCustomerInput())).execute()
            if (!response.hasErrors()) {
                val body = response.data?.addCustomer?.toCustomerGraph()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
                return Error(
                    response.errors?.first()?.message ?: Constantes.SPACE
                )

            }
        } catch (e: Exception) {
            return Error(e.message ?: e.toString())
        }

    }


}