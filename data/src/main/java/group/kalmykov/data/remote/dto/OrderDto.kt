package group.kalmykov.data.remote.dto

import java.util.UUID

data class OrderDto(
    val id: UUID,
    val createdAt: String, // можно парсить в дату при необходимости
    val customerName: String,
    val items: List<OrderItemDto>,
    val totalAmount: Double,
    val status: OrderStatus
)