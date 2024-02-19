package com.example.apolloproject.ui.screens.customers

import androidx.compose.animation.core.tween
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apolloproject.domain.model.CustomerGraph
import java.util.UUID

@Composable
fun CustomersLista(
    viewModel: CustomersListViewModel= hiltViewModel(),
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
   viewModel.event(CustomerListContract.Event.getCustomers)
    }

    ListaCust(
        state = state.value,
        onViewDetalle = onViewDetalle,
        bottomNavigationBar = bottomNavigationBar,
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaCust(
    state: CustomerListContract.State,
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},

    ) {
    val snackbarHostState = remember { SnackbarHostState() }



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
        floatingActionButton = {
            Button(onClick = { /*TODO*/ }) {
                Text("+")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->


        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray)
        ) {

            items(items = state.customers, key = { cust -> cust.id }) { cust ->
                CustomerItem(
                    customer = cust,
                    onViewDetalle = onViewDetalle,
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(1000)
                    )
                )
            }
        }

    }


}
@Composable
fun CustomerItem(
    customer :CustomerGraph,
    onViewDetalle :(Int) -> Unit,
    modifier: Modifier = Modifier

){ Card(modifier = modifier
    .fillMaxWidth()
    .padding(8.dp)
    .clickable { onViewDetalle(customer.id) } ) {
    Row( modifier = Modifier.padding(8.dp)){
        Text(
            modifier = Modifier.weight(weight = 0.4F),
            text = customer.id.toString()

        )
        Text(
            modifier = Modifier.weight(weight = 0.4F),
            text = customer.firstName
        )
        Text(
            modifier = Modifier.weight(0.4F),
            text = customer.lastName)

    }
}

}
