package group.kalmykov.data.remote.dto

data class OrderItemDto(
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double
)
