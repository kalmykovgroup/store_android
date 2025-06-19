package group.kalmykov.store.presentation.components.product_list

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.kalmykov.data.remote.dto.ProductDto
import group.kalmykov.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import group.kalmykov.store.R

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductListUiState.Loading
            val response = repository.getAll()
            _uiState.value = if (response.success && response.data != null) {
                ProductListUiState.Success(response.data!!)
            } else {
                ProductListUiState.Error(response.errorMessage ?: R.string.error_loading_products.toString())
            }
        }
    }

    // keep original signature, externalize only fallback string
    fun uploadProductImage(
        uri: Uri,
        context: Context,
        onResult: (String?, String?) -> Unit
    ) {
        viewModelScope.launch {
            val response = repository.uploadProductImage(uri, context)
            if (response.success && response.data != null) {
                onResult(response.data!!.imageUrl, null)
            } else {
                onResult(
                    null,
                    response.errorMessage ?: context.getString(R.string.error_uploading_image)
                )
            }
        }
    }

    fun addProduct(product: ProductDto) {
        viewModelScope.launch {
            val response = repository.create(product)
            if (response.success && response.data != null) {
                val currentState = _uiState.value
                if (currentState is ProductListUiState.Success) {
                    val updatedList = currentState.products + response.data!!
                    _uiState.value = ProductListUiState.Success(updatedList)
                }
            } else {
                _uiState.value = ProductListUiState.Error(
                    response.errorMessage ?: R.string.error_adding_product.toString()
                )
            }
        }
    }

    fun updateProduct(product: ProductDto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            Log.d("UpdateProduct", "Sending ProductDto: $product")
            val response = repository.update(product)
            if (response.success) {
                Log.d("UpdateProduct", "Response ProductDto: ${response.data}")
                loadProducts()
                onSuccess()
            } else {
                _uiState.value = ProductListUiState.Error(
                    response.errorMessage ?: R.string.error_updating_product.toString()
                )
            }
        }
    }

    fun deleteProduct(id: UUID) {
        viewModelScope.launch {
            val response = repository.delete(id)
            if (response.success) {
                val currentState = _uiState.value
                if (currentState is ProductListUiState.Success) {
                    val updatedList = currentState.products.filterNot { it.id == id }
                    _uiState.value = ProductListUiState.Success(updatedList)
                }
            } else {
                _uiState.value = ProductListUiState.Error(
                    response.errorMessage ?: R.string.error_deleting_product.toString()
                )
            }
        }
    }
}
