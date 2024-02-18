package com.example.apolloproject.ui.screens.logincompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apolloproject.ui.screens.navigation.navigationAct
import com.example.apolloproject.ui.theme.ApolloProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApolloProjectTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "pantallaLogin"
                ) {
                    composable("pantallaLogin") {
                        pantallaLogin(navController)
                    }
                    composable("anavigation") {
                        navigationAct()
                    }
                }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun pantallaLogin(
    navController :NavController= rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ContenidoPantalla(
          navController,
            state,
            viewModel, Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }

}

@Composable
fun ContenidoPantalla(
    navController :NavController,
    state: LoginContract.State,
    viewModel: LoginViewModel? = null,
    align: Modifier,

    ) {
    val context = LocalContext.current // Obtener el contexto de la actividad

    Column(modifier = align) {


        Spacer(modifier = Modifier.padding(16.dp))
        nombreUsuario(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))
        passwUsuario(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))

        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            loginBtn( viewModel)
            Spacer(modifier = Modifier.padding(16.dp))
            registerBtn( viewModel)


        }
        if (state.loginsucces) {
            Toast.makeText(context, "Usuario logeado", Toast.LENGTH_SHORT).show()

            navController.navigate("anavigation")
            viewModel?.event(LoginContract.Event.CambiarLoginSuccess(false))
        }
        if (state.registersuccess){
            Toast.makeText(context, "Usuario añadido", Toast.LENGTH_SHORT).show()
        }

    }
}


@Composable
fun nombreUsuario(state: LoginContract.State, viewModel: LoginViewModel?) {


    state.username?.let { user ->
        TextField(
            value = user,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                viewModel?.event(LoginContract.Event.CambiarUserState(it))

            },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )
    }

}
@Composable
fun loginBtn( viewModel: LoginViewModel?) {
    Button(onClick = { viewModel?.event(LoginContract.Event.login) },

        content = { Text(text = "login") })


}



@Composable
fun registerBtn( viewModel: LoginViewModel?) {

    Button(onClick = { viewModel?.event(LoginContract.Event.register) },

        content = { Text(text = "register") })


}

@Composable
fun passwUsuario(state: LoginContract.State, viewModel: LoginViewModel?) {


    state.password?.let { pass ->
        TextField(
            value = pass,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                viewModel?.event(LoginContract.Event.CambiarPasswState(it))

            },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )
    }
}
}


