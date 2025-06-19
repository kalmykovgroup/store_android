package group.kalmykov.data.remote

import group.kalmykov.data.remote.contracts.ApiResponse
import group.kalmykov.data.remote.contracts.products.commands.AddProductCommand
import group.kalmykov.data.remote.contracts.products.commands.UpdateProductCommand
import group.kalmykov.data.remote.dto.ImageUploadResponse
import group.kalmykov.data.remote.dto.OrderDto
import group.kalmykov.data.remote.dto.ProductDto
import group.kalmykov.data.remote.dto.UpdateOrderStatusRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.UUID


interface ApiService {

    @GET("product/all")
    suspend fun getAllProducts(): ApiResponse<List<ProductDto>>

    @POST("product/create")
    suspend fun addProduct(@Body command: AddProductCommand): ApiResponse<ProductDto>

    @PUT("product/update")
    suspend fun updateProduct(@Body command: UpdateProductCommand): ApiResponse<ProductDto>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") productId: UUID): ApiResponse<Boolean>

    @GET("orders/all")
    suspend fun getAllOrders(): ApiResponse<List<OrderDto>>

    @PUT("orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") id: UUID,
        @Body request: UpdateOrderStatusRequest
    ): ApiResponse<Boolean>

    @Multipart
    @POST("product/upload")
    suspend fun uploadProductImage(
        @Part image: MultipartBody.Part
    ): ApiResponse<ImageUploadResponse>
}