package group.kalmykov.store.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import group.kalmykov.store.R

sealed class NavigationItem(
    val route: String,
    val icon: ImageVector,
    @StringRes val titleRes: Int
) {
    data object Home : NavigationItem(
        route = "home",
        icon = Icons.Filled.Home,
        titleRes = R.string.nav_home
    )

    data object Orders : NavigationItem(
        route = "orders",
        icon = Icons.Filled.List,
        titleRes = R.string.nav_orders
    )

    data object Cart : NavigationItem(
        route = "cart",
        icon = Icons.Filled.ShoppingCart,
        titleRes = R.string.nav_cart
    )

    data object Users : NavigationItem(
        route = "users",
        icon = Icons.Filled.Person,
        titleRes = R.string.nav_users
    )
}