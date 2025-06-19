package group.kalmykov.data.repository

import group.kalmykov.data.remote.contracts.ApiResponse
import group.kalmykov.data.remote.dto.OrderDto
import group.kalmykov.data.remote.dto.OrderStatus
import java.util.UUID

interface OrderRepository {
    suspend fun getAll(): ApiResponse<List<OrderDto>>
    suspend fun updateStatus(id: UUID, status: OrderStatus): ApiResponse<Boolean>
}