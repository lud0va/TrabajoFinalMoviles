package com.example.apolloproject.ui.screens.orders.add

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apolloproject.R
import com.example.apolloproject.domain.model.CustomerGraph


@Composable
fun AddOrder (
    viewModel: AddOrderViewModel = hiltViewModel(),

    ){
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.event(AddOrderContract.Event.GetCustomers)


    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16dp))
    ) {

        ContenidoAddOrder(
            state.value,
            {viewModel.event(AddOrderContract.Event.Addorder)},
            {viewModel.event(AddOrderContract.Event.CambiarCustomerId(it))}
            , Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimen_16dp))
        )
    }





}
@Composable
fun ContenidoAddOrder(
    state: AddOrderContract.State,
    addOrder: ()->Unit,
    cambiarId: (String) -> Unit,
    align: Modifier,

    ){

    Column(modifier = align) {

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))

        CustomerId(state,cambiarId)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
        adddateOrder(state )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
        ListaAddCust(
            addOrder,
            state = state,

            )


    }


}


@Composable
fun CustomerId(state: AddOrderContract.State,cambiarId: (String) -> Unit){

        TextField(
            value = state.orderGraph?.customerId.toString(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {

                    cambiarId(it )


            },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )


}

@Composable
fun adddateOrder(state: AddOrderContract.State) {

        TextField(
            value = state.orderGraph?.orderDate.toString(),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaAddCust(
    addOrder: () -> Unit,
    state: AddOrderContract.State,

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val label= stringResource(id = R.string.ok)
    val plus= stringResource(id = R.string.plus)
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            Button(onClick = { addOrder() }) {
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

            items(items = state.customers, key = { item -> item.id }) { customer ->
                CustomerAddItem(
                    customer = customer,

                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(1000)
                    )
                )
            }
        }
    }
}

@Composable
fun CustomerAddItem(
    customer: CustomerGraph,

    modifier: Modifier = Modifier

) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.dimen_8dp))
      ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp))) {
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = customer.id.toString()

            )
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = customer.firstName
            )

        }
    }

}
