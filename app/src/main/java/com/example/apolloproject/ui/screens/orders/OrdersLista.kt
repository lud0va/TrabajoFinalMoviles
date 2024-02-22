package com.example.apolloproject.ui.screens.orders


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button

import androidx.compose.material3.Card

import androidx.compose.material3.FabPosition

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.apolloproject.R

import com.example.apolloproject.domain.model.OrderGraph

import com.example.apolloproject.ui.screens.customers.SwipeToDeleteContainer

@Composable
fun OrdersLista(
    navController: NavController,
    viewModel: OrdersListaViewModel = hiltViewModel(),
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event(OrdersListaContract.Event.GetOrders)
    }

    OrdersList(navController,
        { viewModel.event((OrdersListaContract.Event.DeleteOrder(it))) },
        state = state.value,
        onViewDetalle = onViewDetalle,
        bottomNavigationBar = bottomNavigationBar,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersList(
    navController: NavController,
    delete:(Int)->Unit,
    state: OrdersListaContract.State,
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val addOrdPath= stringResource(id = R.string.addOrder)
    val label= stringResource(id = R.string.ok)
    val plus= stringResource(id = R.string.plus)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
        floatingActionButton = {
            Button(onClick = { navController.navigate(addOrdPath)}) {
                Text(plus)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        LaunchedEffect(state.message) {
            state.message?.let {
                snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = label
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray)
        ) {

            items(items = state.orders, key = { order -> order.orderId.toString() }) { order ->
                SwipeToDeleteContainer(item = order, onDelete = {
                    it.orderId?.let { it1 -> delete(it1) }
                }) {
                    OrderItem(order = order, onViewDetalle = onViewDetalle)


                }
            }

        }

    }
}





@Composable

fun OrderItem(
    order: OrderGraph,
    onViewDetalle: (Int) -> Unit,
    modifier: Modifier = Modifier

) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.dimen_8dp))
        .clickable { order.orderId?.let { onViewDetalle(it) } }) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp))) {
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = order.orderId.toString()
            )
            Text(
                modifier = Modifier.weight(0.4F),
                text = order.orderDate.toString()
            )

        }
    }


}

