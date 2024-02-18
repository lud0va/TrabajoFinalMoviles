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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apolloproject.domain.model.CommandGraph
import com.example.apolloproject.domain.model.OrderItemGraph

@Composable
fun OrdersDetalle(
    orderId: Int,
    viewModel: OrdersViewModel = hiltViewModel(),

    )
{
    val state = viewModel.uiState.collectAsState()
    // cargar el id en elState
    //para get TABLES AND COMMANDS   viewModel.handleEvent(PantallaListaEvent.GetPersonas)

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ContenidoOrderPantalla(
            state.value,
            viewModel, Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }

}

@Composable
fun ContenidoOrderPantalla(
    state: OrdersDetalleContract.State,
    viewModel: OrdersViewModel? = null,
    align: Modifier,

    ){
    Column(modifier = align) {
        idOrder(state, viewModel)
        Spacer(modifier = Modifier.padding(16.dp))
        dateOrder(state, viewModel)




            ListaTables(
                state = state,

                )


    }


}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaTables(
    state: OrdersDetalleContract.State,

    ) {
    Scaffold() { innerPadding ->


        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray)
        ) {

            items(items = state.ordeItem, key = { item -> item.orderItemId }) { orderItem ->
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
fun idOrder(state: OrdersDetalleContract.State, viewModel: OrdersViewModel?) {
    Text(text = state.orderGraph?.orderId.toString())
}

@Composable
fun dateOrder(state: OrdersDetalleContract.State, viewModel: OrdersViewModel?) {
    state.orderGraph?.orderDate?.let { orderDate ->
        TextField(
            value = orderDate.toString(),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}



@Composable
fun   OrderItem(
    orderItem: OrderItemGraph,

    modifier: Modifier = Modifier

){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
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
