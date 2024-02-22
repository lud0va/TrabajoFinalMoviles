package com.example.apolloproject.ui.screens.customers.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.apolloproject.R
import com.example.apolloproject.domain.model.CustomerGraphDetail

@Composable
fun AddCustomer(
    navController: NavController,
    viewModel: AddCustomerViewModel = hiltViewModel(),

    ) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16dp))
    ) {

        AddCustPantalla(
            state.value,
            navController,
            { viewModel.event(AddCustomerContract.Event.AddCustomer(it)) },
            { viewModel.event(AddCustomerContract.Event.CambiarFirstName(it)) },
            { viewModel.event(AddCustomerContract.Event.CambiarLastName(it)) },
            { viewModel.event(AddCustomerContract.Event.CambiarEmail(it)) },
            { viewModel.event(AddCustomerContract.Event.CambiarPhone(it)) }

        )
    }


}

@Composable
fun AddCustPantalla(

    state: AddCustomerContract.State,
    navController: NavController,
    update: (CustomerGraphDetail) -> Unit,
    cambiarFirst: (String) -> Unit,
    cambiarLast: (String) -> Unit,
    cambiarEmail: (String) -> Unit,
    cambiarPhone: (String) -> Unit

) {
    val snackbarHostState = remember { SnackbarHostState() }
    val ok = stringResource(id = R.string.ok)
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        LaunchedEffect(state.message) {
            state.message?.let {

                snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = ok
                )
            }

        }
        Column(modifier = Modifier.padding(innerPadding)) {


            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
            firstName(state, cambiarFirst)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            lastName(state, cambiarLast)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            email(state, cambiarEmail)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            phone(state, cambiarPhone)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            addCustomer(state, update)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
            backClist(navController )

        }
    }


}

@Composable
fun backClist(

   navController: NavController
) {
    val path= stringResource(id = R.string.customerListPath)
    Button(onClick = { navController.navigate(path) },

        content = { Text(text = stringResource(id = R.string.back)) })


}
    @Composable
fun addCustomer(
    state: AddCustomerContract.State,
    update: (CustomerGraphDetail) -> Unit
) {
    Button(onClick = { state.customer?.let { update(it) } },

        content = { Text(text = stringResource(id = R.string.add)) })


}

@Composable
fun firstName(state: AddCustomerContract.State, cambianFirst: (String) -> Unit) {



        TextField(
            value = state.customer?.firstName?:"",
            placeholder = { Text(text = stringResource(id = R.string.firstname)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { cambianFirst(it) },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )

}

@Composable
fun lastName(state: AddCustomerContract.State, cambiarLast: (String) -> Unit) {

        TextField(
            value = state.customer?.lastName?:"",
            placeholder = { Text(text = stringResource(id = R.string.lastname)) },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { cambiarLast(it) },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )

}

@Composable
fun email(state: AddCustomerContract.State, cambiarEmail: (String) -> Unit) {

        TextField(
            value = state.customer?.email?:"",


            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = stringResource(id = R.string.email)) },
            onValueChange = { cambiarEmail(it) },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )

}

@Composable
fun phone(state: AddCustomerContract.State, cambiarPhone: (String) -> Unit) {

        TextField(
            value = state.customer?.phone?:"",
            placeholder = { Text(text = stringResource(id = R.string.phone)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { cambiarPhone(it) },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )

}
