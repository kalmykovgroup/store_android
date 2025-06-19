package group.kalmykov.store.presentation.components.product_list

import group.kalmykov.data.remote.dto.ProductDto

sealed class ProductListUiState {
    data object Loading : ProductListUiState()
    data class Success(val products: List<ProductDto>) : ProductListUiState()
    data class Error(val message: String) : ProductListUiState()
}