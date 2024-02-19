package com.example.apolloproject.ui.screens.customers.detalle

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.apolloproject.domain.model.CommandGraph
import com.example.apolloproject.domain.model.CustomerGraph
import com.example.apolloproject.domain.model.TablesGraph
import com.example.apolloproject.ui.screens.customers.CustomerItem
import com.example.apolloproject.ui.screens.customers.CustomersListViewModel
import com.example.apolloproject.ui.screens.customers.ListaCust
import com.example.apolloproject.ui.screens.logincompose.LoginContract
import com.example.apolloproject.ui.screens.logincompose.LoginViewModel

@Composable
fun CustomerDetalle(
    customerId: Int,
    viewModel: CustomerDetalleViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    viewModel.event(CustomerDetalleContract.Event.GetCustomer(customerId))
    viewModel.event(CustomerDetalleContract.Event.GetTables(customerId))

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ContenidoCustPantalla(
            state.value,
            viewModel, Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }


}

@Composable
fun ContenidoCustPantalla(

    state: CustomerDetalleContract.State,
    viewModel: CustomerDetalleViewModel? = null,
    align: Modifier,

    ) {
    Column(modifier = align) {
        idCust(state, viewModel)
        Spacer(modifier = Modifier.padding(16.dp))
        firstNameCust(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))
        lastNameCust(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))
        emailCust(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))
        phoneCust(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))
        dateOfBirth(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))

        Text(text = "table list")
        Spacer(modifier = Modifier.padding(6.dp))
        ListaTables(
            state = state,

            )

    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaTables(
    state: CustomerDetalleContract.State,

    ) {
    Scaffold() { innerPadding ->


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
fun idCust(state: CustomerDetalleContract.State, viewModel: CustomerDetalleViewModel?) {
    Text(text = state.customer?.id.toString())
}

@Composable
fun firstNameCust(state: CustomerDetalleContract.State, viewModel: CustomerDetalleViewModel?) {
    state.customer?.firstName?.let { firstname ->
        TextField(
            value = firstname,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun lastNameCust(state: CustomerDetalleContract.State, viewModel: CustomerDetalleViewModel?) {
    state.customer?.firstName?.let { firstname ->
        TextField(
            value = firstname,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun emailCust(state: CustomerDetalleContract.State, viewModel: CustomerDetalleViewModel?) {
    state.customer?.email?.let { email ->
        TextField(
            value = email,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun phoneCust(state: CustomerDetalleContract.State, viewModel: CustomerDetalleViewModel?) {
    state.customer?.phone?.let { phone ->
        TextField(
            value = phone,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun dateOfBirth(state: CustomerDetalleContract.State, viewModel: CustomerDetalleViewModel?) {
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
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
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

