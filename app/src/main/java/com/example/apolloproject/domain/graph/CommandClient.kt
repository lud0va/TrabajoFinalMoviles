package com.example.apolloproject.domain.graph

import com.example.apolloproject.domain.model.CommandGraph

interface CommandClient {
  suspend fun  getCommandsByCustomer(idCust:Int):List<CommandGraph>
}