package com.example.apolloproject.data

import com.example.apolloproject.data.apollo.graphql.ApolloCustomersRemoteDataSource
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class CustomersRepository @Inject constructor(
    private val customerRemoteDataSource: ApolloCustomersRemoteDataSource
)  {
    fun getCustomers(): Flow<NetworkResult<List<CustomerGraph>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= customerRemoteDataSource.getCustomers()
            emit(result)
        }

    }
    fun addCustomer(input: CustomerGraphDetail): Flow<NetworkResult<CustomerGraphDetail?>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=customerRemoteDataSource.addCustomer(input)
            emit(result)
        }
    }

    fun getCustomer(id:Int):Flow<NetworkResult<CustomerGraphDetail?>>{

        return flow {
            emit(NetworkResult.Loading())
            val result=customerRemoteDataSource.getCustomer(id)
            emit(result)
        }
    }
    fun deleteCustomer(idCustomer: Int):Flow< NetworkResult<Boolean?>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=customerRemoteDataSource.deleteCustomer(idCustomer)
            emit(result)
        }
    }
    fun updateCustomer(input: CustomerGraphDetail): Flow<NetworkResult<CustomerGraphDetail?>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=customerRemoteDataSource.updateCustomer(input)
            emit(result)
        }
    }
}