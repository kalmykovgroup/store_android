package group.kalmykov.store.presentation.components.order_list

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import group.kalmykov.data.remote.dto.OrderStatus

@Composable
fun StatusDropdown(
    currentStatus: OrderStatus,
    onStatusChange: (OrderStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val allStatuses = OrderStatus.entries

    Box {
        Button(onClick = { expanded = true }) {
            Text(text = "Изменить статус")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            allStatuses.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.name) },
                    onClick = {
                        expanded = false
                        if (status != currentStatus) {
                            onStatusChange(status)
                        }
                    }
                )
            }
        }
    }
}