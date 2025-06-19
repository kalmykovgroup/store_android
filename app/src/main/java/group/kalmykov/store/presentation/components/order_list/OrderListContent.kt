package group.kalmykov.store.presentation.components.order_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import group.kalmykov.data.remote.dto.OrderDto
import group.kalmykov.data.remote.dto.OrderStatus

@Composable
fun OrderListContent(
    orders: List<OrderDto>,
    onStatusChange: (OrderDto, OrderStatus) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(orders, key = { it.id }) { order ->
            OrderItemCard(
                order = order,
                onStatusChange = { newStatus -> onStatusChange(order, newStatus) }
            )
        }
    }
}
