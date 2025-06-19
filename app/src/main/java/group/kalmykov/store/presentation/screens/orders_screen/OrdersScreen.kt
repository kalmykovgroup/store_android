package group.kalmykov.store.presentation.screens.orders_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import group.kalmykov.store.presentation.components.order_list.OrderListUiState
import group.kalmykov.store.presentation.components.order_list.OrderListViewModel
import group.kalmykov.store.presentation.components.order_list.OrderListContent

@Composable
fun OrdersScreen(
    viewModel: OrderListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadOrders()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text(
                text = "Список заказов",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск по имени клиента") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (val state = uiState) {
                is OrderListUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is OrderListUiState.Success -> {
                    val filteredOrders = state.orders.filter {
                        it.customerName.contains(searchQuery, ignoreCase = true)
                    }

                    if (filteredOrders.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Нет заказов", style = MaterialTheme.typography.bodyLarge)
                        }
                    } else {
                        OrderListContent(
                            orders = filteredOrders,
                            onStatusChange = { order, newStatus ->
                                viewModel.updateOrderStatus(order.id, newStatus)
                            }
                        )
                    }
                }

                is OrderListUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Ошибка: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
