package com.example.apolloproject.ui.screens.customers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apolloproject.R
import com.example.apolloproject.domain.model.CustomerGraph
import kotlinx.coroutines.delay

@Composable
fun CustomersLista(
    viewModel: CustomersListViewModel = hiltViewModel(),
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.event(CustomerListContract.Event.getCustomers)
    }
    val snackbarHostState = remember { SnackbarHostState() }



    ListaCust(
        viewModel,
        state = state.value,
        onViewDetalle = onViewDetalle,
        bottomNavigationBar = bottomNavigationBar,
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaCust(
    viewModel: CustomersListViewModel?=null,
    state: CustomerListContract.State,
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},

    ) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error){
        state.error?.let {
            snackbarHostState.showSnackbar(
                message = it,
                actionLabel = "OK"
            )
        }
    }


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
                SwipeToDeleteContainer(item = cust, onDelete ={
                    viewModel?.event(CustomerListContract.Event.deleteCustomer(cust.id)) }) { customer->
                    CustomerItem(
                        customer = customer,
                        onViewDetalle = onViewDetalle
                    )
                }


            }
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }

        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(
                durationMillis = animationDuration
            ),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(
                    swipeDismissState = state,

                    )
            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else {
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
          contentDescription = null,
            tint = Color.White,
        )
    }
}

    @Composable
    fun CustomerItem(
        customer: CustomerGraph,
        onViewDetalle: (Int) -> Unit,
        modifier: Modifier = Modifier

    ) {
        Card(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onViewDetalle(customer.id) }) {
            Row(modifier = Modifier.padding(8.dp)) {
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
                    text = customer.lastName
                )

            }
        }

    }
