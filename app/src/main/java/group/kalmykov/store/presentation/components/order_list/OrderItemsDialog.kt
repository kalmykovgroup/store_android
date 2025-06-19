package group.kalmykov.store.presentation.components.order_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import group.kalmykov.data.remote.dto.OrderItemDto
import group.kalmykov.store.R

@Composable
fun OrderItemsDialog(
    items: List<OrderItemDto>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.order_items_title)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                items.forEach { item ->
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(
                            text = stringResource(R.string.order_item_name, item.productName),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = stringResource(R.string.order_item_quantity, item.quantity),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(R.string.order_item_unit_price, item.unitPrice),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(R.string.order_item_total_price, item.totalPrice),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.close_button))
            }
        }
    )
}