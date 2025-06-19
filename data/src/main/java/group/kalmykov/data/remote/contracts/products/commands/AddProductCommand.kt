package group.kalmykov.data.remote.contracts.products.commands

import group.kalmykov.data.remote.dto.ProductDto

data class AddProductCommand(
    val productDto: ProductDto
)