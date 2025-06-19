package group.kalmykov.store.presentation.components.order_list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import group.kalmykov.data.remote.dto.OrderStatus
import group.kalmykov.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import group.kalmykov.store.R

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val repository: OrderRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<OrderListUiState>(OrderListUiState.Loading)
    val uiState: StateFlow<OrderListUiState> = _uiState

    fun loadOrders() {
        viewModelScope.launch {
            _uiState.value = OrderListUiState.Loading
            val response = repository.getAll()
            _uiState.value = if (response.success && response.data != null) {
                OrderListUiState.Success(response.data!!)
            } else {
                OrderListUiState.Error(
                    response.errorMessage ?: context.getString(R.string.error_loading_orders)
                )
            }
        }
    }

    fun updateOrderStatus(orderId: UUID, status: OrderStatus) {
        viewModelScope.launch {
            val response = repository.updateStatus(orderId, status)
            if (response.success) {
                val currentState = _uiState.value
                if (currentState is OrderListUiState.Success) {
                    val updatedList = currentState.orders.map { order ->
                        if (order.id == orderId) order.copy(status = status) else order
                    }
                    _uiState.value = OrderListUiState.Success(updatedList)
                }
            } else {
                _uiState.value = OrderListUiState.Error(
                    response.errorMessage ?: context.getString(R.string.error_updating_order)
                )
            }
        }
    }
}
