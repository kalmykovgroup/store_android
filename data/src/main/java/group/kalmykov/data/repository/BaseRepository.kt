package group.kalmykov.data.repository

import group.kalmykov.data.remote.contracts.ApiResponse
import java.util.UUID

interface BaseRepository<T> {

    suspend fun getAll(): ApiResponse<List<T>>
    suspend fun create(item: T): ApiResponse<T>
    suspend fun update(item: T): ApiResponse<T>
    suspend fun delete(id: UUID): ApiResponse<Boolean>
}