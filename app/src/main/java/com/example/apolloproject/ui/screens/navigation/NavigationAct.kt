package com.example.apolloproject.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apolloproject.ui.common.BottomBar
import com.example.apolloproject.ui.screens.customers.CustomersLista
import com.example.apolloproject.ui.screens.customers.detalle.CustomerDetalle
import com.example.apolloproject.ui.screens.orders.OrdersLista
import com.example.apolloproject.ui.screens.orders.detalle.OrdersDetalle

@Composable
fun navigationAct() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "customersList",

    ){
        composable(
            "customersList"
        ){
            CustomersLista(
                onViewDetalle = {int ->
                    navController.navigate("detalleCust/${int}")
                },
                bottomNavigationBar =  {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar)
                }
            )

        }
        composable(
            route =  "detalleCust/{customerId}",
            arguments = listOf(
                navArgument(name = "customerId") {
                    type = NavType.IntType
                    defaultValue = ""
                }
            )
        ){
            CustomerDetalle(
                customerId=it.arguments?.getInt("customerId")?:0,
            )
        }
        composable(
            "ordersList"
        ){
            OrdersLista(
                onViewDetalle = {int ->
                    navController.navigate("detalleOrder/${int}")
                },
                bottomNavigationBar =  {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar)
                }
            )
        }
        composable(
            route =  "detalleOrder/{orderId}",
            arguments = listOf(
                navArgument(name = "orderId") {
                    type = NavType.IntType
                    defaultValue = ""
                }
            )
        ){
            OrdersDetalle(
                orderId = it.arguments?.getInt("orderId")?:0,
            )
        }
    }

}