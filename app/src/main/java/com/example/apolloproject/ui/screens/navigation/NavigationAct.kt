package com.example.apolloproject.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apolloproject.R
import com.example.apolloproject.ui.common.BottomBar
import com.example.apolloproject.ui.screens.customers.CustomersLista
import com.example.apolloproject.ui.screens.customers.add.AddCustomer
import com.example.apolloproject.ui.screens.customers.detalle.CustomerDetalle
import com.example.apolloproject.ui.screens.logincompose.pantallaLogin
import com.example.apolloproject.ui.screens.orders.OrdersLista
import com.example.apolloproject.ui.screens.orders.add.AddOrder
import com.example.apolloproject.ui.screens.orders.detalle.OrdersDetalle
import com.example.apolloproject.ui.screens.splashscreen.SplashScreen

@Composable
fun navigationAct() {
    val navController = rememberNavController()
    val splashRoute= stringResource(id = R.string.splash)
    val pantallaLogin=   stringResource(id = R.string.pantallaLogin)
    val cusotmerList= stringResource(id = R.string.customerListPath)
    val addOrder= stringResource(id =R.string.addOrder )
    val customerId= stringResource(id = R.string.customerId)
    val detalleCustPath= stringResource(id = R.string.detalleCustPath)
    val addCustomer= stringResource(id = R.string.addCustomer)
    val ordersList= stringResource(id = R.string.ordersList)
    val orderDetallePath= stringResource(id = R.string.detalleOrderPath)
    val orderId= stringResource(id = R.string.orderId)
    NavHost(
        navController = navController,
        startDestination = splashRoute,

    ){
        composable(
            splashRoute
        ){
            SplashScreen(navController = navController)
        }
        composable(pantallaLogin) {
            pantallaLogin(navController)
        }


        composable(
            cusotmerList
        ){
            CustomersLista(
                navController,
                onViewDetalle = {customerId ->
                    navController.navigate("detalleCust/${customerId}")
                },
                bottomNavigationBar =  {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar)
                }
            )

        }
        composable(
            route =  detalleCustPath,
            arguments = listOf(
                navArgument(name = customerId) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            CustomerDetalle(
                customerId=it.arguments?.getInt(customerId) ?: 0,
            )
        }
        composable(
            addOrder
        ){
            AddOrder()

        }

        composable(
          addCustomer
        ){

            AddCustomer(navController)
        }
        composable(
            ordersList
        ){
            OrdersLista(
                navController,
                onViewDetalle = {orderId ->
                    navController.navigate("detalleOrder/${orderId}")
                },
                bottomNavigationBar =  {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar)
                }
            )
        }
        composable(
            route =  orderDetallePath,
            arguments = listOf(
                navArgument(name = orderId) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            OrdersDetalle(
                orderId = it.arguments?.getInt(orderId)?:0,
            )
        }
    }

}