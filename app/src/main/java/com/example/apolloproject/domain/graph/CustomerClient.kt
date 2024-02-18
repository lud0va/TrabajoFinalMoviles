package com.example.apolloproject.domain.graph

import com.apolloproject.type.Customer
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.CustomerGraphDetail
import dagger.Module
import dagger.Provides

interface CustomerClient {

    suspend fun getCustomer(id:Int):CustomerGraphDetail?

    suspend fun getCustomers(): List<CustomerGraph>

}