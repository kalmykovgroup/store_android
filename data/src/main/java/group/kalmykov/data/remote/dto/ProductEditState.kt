package group.kalmykov.data.remote.dto

import java.util.UUID

data class ProductEditState(
    val id: UUID,
    val name: String,
    val price: String,
    val description: String,
    val imageUrl: String?
)
