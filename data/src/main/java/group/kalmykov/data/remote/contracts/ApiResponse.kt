package group.kalmykov.data.remote.contracts


data class ApiResponse<T>(
    val success: Boolean,
    val errorMessage: String? = null,
    val data: T? = null
)


fun <T> ApiResponse<T>.isSuccess(): Boolean = success

fun <T> successResponse(data: T): ApiResponse<T> {
    return ApiResponse(success = true, data = data)
}

fun <T> failResponse(message: String): ApiResponse<T> {
    return ApiResponse(success = false, errorMessage = message, data = null)
}
