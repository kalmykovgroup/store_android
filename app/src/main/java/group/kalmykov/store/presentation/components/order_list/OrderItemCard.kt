package group.kalmykov.store.presentation.components.order_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import group.kalmykov.data.remote.dto.OrderDto
import group.kalmykov.data.remote.dto.OrderStatus
import group.kalmykov.store.R
import group.kalmykov.store.ui.theme.AppColors

@Composable
fun OrderItemCard(
    order: OrderDto,
    onStatusChange: (OrderStatus) -> Unit
) {
    var showItemsDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    text = stringResource(R.string.order_status_label),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = order.status.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = statusColor(order.status),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    text = stringResource(R.string.order_date_label),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = order.createdAt,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = stringResource(R.string.order_client_label),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = order.customerName,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = stringResource(R.string.order_total_label),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.order_total_format, order.totalAmount),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { showItemsDialog = true }) {
                    Text(stringResource(R.string.view_items_button))
                }

                StatusDropdown(
                    currentStatus = order.status,
                    onStatusChange = onStatusChange
                )
            }

            if (showItemsDialog) {
                OrderItemsDialog(
                    items = order.items,
                    onDismiss = { showItemsDialog = false }
                )
            }
        }
    }
}


fun statusColor(status: OrderStatus) = when (status) {
    OrderStatus.NEW -> AppColors.OrderStatusNew
    OrderStatus.PROCESSING -> AppColors.OrderStatusProcessing
    OrderStatus.SHIPPED -> AppColors.OrderStatusShipped
    OrderStatus.DELIVERED -> AppColors.OrderStatusDelivered
    OrderStatus.CANCELLED -> AppColors.OrderStatusCancelled
}
