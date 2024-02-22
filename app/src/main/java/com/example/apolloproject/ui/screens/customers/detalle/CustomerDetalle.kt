package com.example.apolloproject.ui.screens.customers.detalle

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.FabPosition
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
import com.example.apolloproject.domain.model.CustomerGraphDetail
import com.example.apolloproject.domain.model.TablesGraph

@Composable
fun CustomerDetalle(
    customerId: Int,
    viewModel: CustomerDetalleViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    if (state.value.customer==null){
        viewModel.event(CustomerDetalleContract.Event.GetCustomer(customerId))
        viewModel.event(CustomerDetalleContract.Event.GetTables(customerId))
    }





    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_8dp))
    ) {

        ContenidoCustPantalla(
            state.value,
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimen_8dp)),
            { viewModel.event(CustomerDetalleContract.Event.UpdateCustomer(it)) },
            { viewModel.event(CustomerDetalleContract.Event.CambiarFirstName(it)) },
            { viewModel.event(CustomerDetalleContract.Event.CambiarLastName(it)) },
            {viewModel.event(CustomerDetalleContract.Event.CambiarEmail(it))},
            {viewModel.event(CustomerDetalleContract.Event.CambiarPhone(it))}

        )
    }


}

@Composable
fun ContenidoCustPantalla(

    state: CustomerDetalleContract.State,
    align: Modifier,
    update: (CustomerGraphDetail) -> Unit,
    cambiarFirst: (String) -> Unit,
    cambiarLast: (String) -> Unit,
    cambiarEmail: (String) -> Unit,
    cambiarPhone: (String) -> Unit

) {
    Column(modifier = align) {
        Row( modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp)),  horizontalArrangement = Arrangement.SpaceBetween){
            idCust(state)
            Spacer(modifier = Modifier.weight(1f))

            updateCustomer(state, update)
        }


        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
        firstNameCust(state,cambiarFirst)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        lastNameCust(state,cambiarLast)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        emailCust(state,cambiarEmail)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        phoneCust(state, cambiarPhone)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        dateOfBirth(state)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))

        Text(text = stringResource(id = R.string.tableList))
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        ListaTables(
            state = state,

            )




    }


}


@Composable
fun updateCustomer(
    state: CustomerDetalleContract.State,
    update: (CustomerGraphDetail) -> Unit
) {
    Button(onClick = { state.customer?.let { update(it) } },

        content = { Text(text = stringResource(id = R.string.update)) })


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaTables(
    state: CustomerDetalleContract.State,

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val label= stringResource(id = R.string.ok)
    Scaffold(
        snackbarHost ={SnackbarHost(snackbarHostState)},
        floatingActionButtonPosition = FabPosition.Center) { innerPadding ->

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

            items(items = state.tables, key = { table -> table.id }) { table ->
                TableItem(
                    table = table,

                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(1000)
                    )
                )
            }
        }
    }
}

@Composable
fun idCust(state: CustomerDetalleContract.State) {
    Text(text = state.customer?.id.toString())
}

@Composable
fun firstNameCust(state: CustomerDetalleContract.State, cambianFirst: (String) -> Unit) {


    state.customer?.firstName?.let { firstname ->
        TextField(
            value = firstname,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {cambianFirst(it)},
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun lastNameCust(state: CustomerDetalleContract.State,cambiarLast: (String) -> Unit) {
    state.customer?.lastName?.let { lastname ->
        TextField(
            value = lastname,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {cambiarLast(it)},
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun emailCust(state: CustomerDetalleContract.State,cambiarEmail: (String) -> Unit) {
    state.customer?.email?.let { email ->
        TextField(
            value = email,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {cambiarEmail(it)},
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun phoneCust(state: CustomerDetalleContract.State, cambiarPhone: (String) -> Unit) {
    state.customer?.phone?.let { phone ->
        TextField(
            value = phone,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {cambiarPhone(it)},
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun dateOfBirth(state: CustomerDetalleContract.State) {
    state.customer?.dateOfBirth?.let { date ->
        TextField(
            value = date.toString(),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun TableItem(
    table: TablesGraph,

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
                text = table.id.toString()

            )
            Text(
                modifier = Modifier.weight(0.4F),
                text = table.numberOfSeats.toString()

            )

        }
    }

}

