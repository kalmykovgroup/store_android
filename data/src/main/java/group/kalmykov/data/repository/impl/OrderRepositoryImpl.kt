package group.kalmykov.data.repository.impl

import group.kalmykov.data.remote.ApiService
import group.kalmykov.data.remote.contracts.ApiResponse
import group.kalmykov.data.remote.dto.OrderDto
import group.kalmykov.data.remote.dto.OrderStatus
import group.kalmykov.data.remote.dto.UpdateOrderStatusRequest
import group.kalmykov.data.repository.OrderRepository
import group.kalmykov.data.utils.safeApiCall
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val api: ApiService
) : OrderRepository {

    override suspend fun getAll(): ApiResponse<List<OrderDto>> {
        return safeApiCall { api.getAllOrders() }
    }

    override suspend fun updateStatus(id: UUID, status: OrderStatus): ApiResponse<Boolean> {
        val request = UpdateOrderStatusRequest(status.name)
        return safeApiCall { api.updateOrderStatus(id, request) }
    }
}