package group.kalmykov.data.utils

import group.kalmykov.data.remote.contracts.ApiResponse
import group.kalmykov.data.remote.contracts.failResponse
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> ApiResponse<T>): ApiResponse<T> {
    return try {
        apiCall()
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> failResponse("Ошибка сети: ${throwable.localizedMessage}")
            is HttpException -> failResponse("Ошибка сервера: ${throwable.code()} ${throwable.message()}")
            else -> failResponse("Неизвестная ошибка: ${throwable.localizedMessage}")
        }
    }
}