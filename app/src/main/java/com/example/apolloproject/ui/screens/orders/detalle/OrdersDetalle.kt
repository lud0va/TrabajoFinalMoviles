package com.example.apolloproject.ui.screens.orders.detalle

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apolloproject.R
import com.example.apolloproject.domain.model.OrderItemGraph

@Composable
fun OrdersDetalle(
    orderId: Int,
    viewModel: OrdersViewModel = hiltViewModel(),

    ) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.event(OrdersDetalleContract.Event.GetOrder(orderId))
    viewModel.event(OrdersDetalleContract.Event.GetOrderItems(orderId))

    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16dp))
    ) {

        ContenidoOrderPantalla(
            state.value,
            {viewModel.event(OrdersDetalleContract.Event.AddordeItem)}
            , Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimen_16dp))
        )
    }

}

@Composable
fun ContenidoOrderPantalla(
    state: OrdersDetalleContract.State,
    addItem: ()->Unit,
    align: Modifier,

    ) {
    Column(modifier = align) {
        idOrder(state, )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
        dateOrder(state, )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
        ListaItems(
            addItem,
            state = state,

            )


    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaItems(
    addItem: () -> Unit,
    state: OrdersDetalleContract.State,

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val label= stringResource(id = R.string.ok)
    val plus= stringResource(id = R.string.plus)
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            Button(onClick = { addItem() }) {
                Text(plus)
            }
        },
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

            items(items = state.ordeItem, key = { item -> item.orderItemId?:0 }) { orderItem ->
                OrderItem(
                    orderItem = orderItem,

                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(1000)
                    )
                )
            }
        }
    }
}

@Composable
fun idOrder(state: OrdersDetalleContract.State) {
    Text(text = state.orderGraph?.orderId.toString())
}

@Composable
fun dateOrder(state: OrdersDetalleContract.State) {

        TextField(
            value = state.orderGraph?.orderDate.toString() ,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )

}


@Composable
fun OrderItem(
    orderItem: OrderItemGraph,

    modifier: Modifier = Modifier

) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimen_8dp))
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp))) {
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = orderItem.orderItemId.toString()

            )
            Text(
                modifier = Modifier.weight(0.4F),
                text = orderItem.name

            )
            Text(
                modifier = Modifier.weight(0.4F),
                text = orderItem.price.toString()

            )


        }
    }

}
