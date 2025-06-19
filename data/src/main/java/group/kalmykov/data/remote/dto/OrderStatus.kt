package group.kalmykov.data.remote.dto

enum class OrderStatus {
    NEW,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    companion object {
        fun fromString(value: String): OrderStatus {
            return try {
                valueOf(value)
            } catch (e: Exception) {
                NEW
            }
        }
    }
}
