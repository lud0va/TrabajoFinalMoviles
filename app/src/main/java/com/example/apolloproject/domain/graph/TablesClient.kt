package com.example.apolloproject.domain.graph

import com.example.apolloproject.domain.model.TablesGraph

interface TablesClient {
    suspend fun getAllTablesByCustomer(idCust:Int):List<TablesGraph>
}