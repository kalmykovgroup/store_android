package group.kalmykov.data.repository

import android.content.Context
import android.net.Uri
import group.kalmykov.data.remote.contracts.ApiResponse
import group.kalmykov.data.remote.dto.ImageUploadResponse
import group.kalmykov.data.remote.dto.ProductDto
import java.util.UUID

interface ProductRepository {
    suspend fun getAll(): ApiResponse<List<ProductDto>>
    suspend fun create(item: ProductDto): ApiResponse<ProductDto>
    suspend fun update(item: ProductDto): ApiResponse<ProductDto>
    suspend fun delete(id: UUID): ApiResponse<Boolean>

    suspend fun uploadProductImage(uri: Uri, context: Context): ApiResponse<ImageUploadResponse>
}


