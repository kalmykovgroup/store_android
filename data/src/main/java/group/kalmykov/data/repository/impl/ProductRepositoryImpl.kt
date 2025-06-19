package group.kalmykov.data.repository.impl

import android.content.Context
import android.net.Uri
import group.kalmykov.data.remote.ApiService
import group.kalmykov.data.remote.contracts.ApiResponse
import group.kalmykov.data.remote.contracts.products.commands.AddProductCommand
import group.kalmykov.data.remote.contracts.products.commands.UpdateProductCommand
import group.kalmykov.data.remote.dto.ImageUploadResponse
import group.kalmykov.data.remote.dto.ProductDto
import group.kalmykov.data.repository.ProductRepository
import group.kalmykov.data.utils.prepareFilePart
import group.kalmykov.data.utils.safeApiCall
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {

    override suspend fun getAll(): ApiResponse<List<ProductDto>> {
        return safeApiCall { api.getAllProducts() }
    }

    override suspend fun create(item: ProductDto): ApiResponse<ProductDto> {
        val command = AddProductCommand(item)
        return safeApiCall { api.addProduct(command) }
    }

    override suspend fun update(item: ProductDto): ApiResponse<ProductDto> {
        val command = UpdateProductCommand(item)
        return safeApiCall { api.updateProduct(command) }
    }

    override suspend fun delete(id: UUID): ApiResponse<Boolean> {
        return safeApiCall { api.deleteProduct(id) }
    }

    override suspend fun uploadProductImage(uri: Uri, context: Context): ApiResponse<ImageUploadResponse> {
        val part = prepareFilePart(uri, context)
        return safeApiCall { api.uploadProductImage(part) }
    }
}
