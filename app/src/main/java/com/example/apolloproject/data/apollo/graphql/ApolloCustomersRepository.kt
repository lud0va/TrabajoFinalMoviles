package com.example.apolloproject.data.apollo.graphql

import com.apollographql.apollo3.ApolloClient
import com.apolloproject.AddCustomerMutation
import com.apolloproject.GetCustomerQuery
import com.apolloproject.GetCustomersQuery
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerDetail
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerGraph
import com.example.apolloproject.data.apollo.graphql.mappers.toCustomerInput
import com.example.apolloproject.domain.graph.CustomerClient
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import javax.inject.Inject


class ApolloCustomersRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : CustomerClient {
    override suspend fun getCustomer(id:Int): CustomerGraphDetail? {
        return apolloClient
            .query(GetCustomerQuery(id))
            .execute()
            .data
            ?.getCustomer?.toCustomerDetail()

    }

    override suspend fun getCustomers(): List<CustomerGraph> {
        return apolloClient
            .query(GetCustomersQuery())
            .execute()
            .data
            ?.getCustomers
            ?.map { it.toCustomerGraph() }
            ?: emptyList()


    }

    override suspend fun addCustomer(input: CustomerGraphDetail): CustomerGraph? {
        return apolloClient
            .mutation(AddCustomerMutation(input.toCustomerInput()))
            .execute()
            .data
            ?.addCustomer
            ?.toCustomerGraph()


    }


}