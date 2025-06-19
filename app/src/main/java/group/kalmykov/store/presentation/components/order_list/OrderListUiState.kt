package group.kalmykov.store.presentation.components.order_list

import group.kalmykov.data.remote.dto.OrderDto

sealed class OrderListUiState {
    data object Loading : OrderListUiState()
    data class Success(val orders: List<OrderDto>) : OrderListUiState()
    data class Error(val message: String) : OrderListUiState()
}