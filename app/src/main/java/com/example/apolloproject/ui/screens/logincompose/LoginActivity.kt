package com.example.apolloproject.ui.screens.logincompose



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController
import com.example.apolloproject.R


@Composable
fun pantallaLogin(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16dp))
    ) {

        ContenidoPantalla(
            navController,
            state,
            viewModel,
            { viewModel.event(LoginContract.Event.login) },
            { viewModel.event(LoginContract.Event.register) },
            { viewModel.event(LoginContract.Event.CambiarPasswState(it)) },
            { viewModel.event(LoginContract.Event.CambiarUserState(it)) }


        )
    }

}

@Composable
fun ContenidoPantalla(
    navController: NavController,
    state: LoginContract.State,
    viewModel: LoginViewModel? = null,

    login: () -> Unit,
    regist: () -> Unit,
    cambiarPassw: (String) -> Unit,
    cambiarUser: (String) -> Unit


) {
    val label= stringResource(id = R.string.ok)
    val navCustList= stringResource(id = R.string.customerListPath)
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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

        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {


            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
            nombreUsuario(state, cambiarUser)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            passwUsuario(state, cambiarPassw)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6dp)))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dimen_16dp)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                loginBtn(login)
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
                registerBtn(regist)


            }

            if (state.loginsucces) {
                LaunchedEffect(state.loginsucces) {
                    viewModel?.event(LoginContract.Event.CambiarLoginSuccess(false))

                    navController.navigate(navCustList)
                }
            }


        }
    }
}


@Composable
fun nombreUsuario(state: LoginContract.State, cambiarUser: (String) -> Unit) {



        TextField(
            value = state.username?:"",
            placeholder = { Text(text = stringResource(id = R.string.usuario)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                cambiarUser(it)
                            },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )


}

@Composable
fun loginBtn(login: () -> Unit) {
    Button(onClick = {login() },

        content = { Text(text = stringResource(id = R.string.login)) })


}


@Composable
fun registerBtn(regist: () -> Unit) {

    Button(onClick = { regist()},

        content = { Text(text = stringResource(id = R.string.register)) })


}

@Composable
fun passwUsuario(state: LoginContract.State, cambiarPassw: (String) -> Unit) {



        TextField(
            value = state.password?:"",
            placeholder = { Text(text = stringResource(id = R.string.passw)) },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                cambiarPassw(it)

            },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )

}



