package group.kalmykov.store.presentation.layouts.main_layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import group.kalmykov.store.presentation.components.header.Header
import group.kalmykov.store.presentation.navigation.NavigationItem
import group.kalmykov.store.presentation.screens.home_screen.HomeScreen
import group.kalmykov.store.presentation.screens.users_screen.ProfileScreen
import group.kalmykov.store.presentation.screens.cart_screen.CartScreen
import group.kalmykov.store.presentation.screens.orders_screen.OrdersScreen

@Composable
fun MainLayout() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { Header() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Home.route) {
                HomeScreen()
            }
            composable(NavigationItem.Orders.route) {
                OrdersScreen()
            }
            composable(NavigationItem.Cart.route) {
                CartScreen()
            }
            composable(NavigationItem.Users.route) {
                ProfileScreen()
            }
        }
    }
}


