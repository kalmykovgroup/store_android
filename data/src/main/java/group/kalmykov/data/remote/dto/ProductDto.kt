package group.kalmykov.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ProductDto(
    val id: UUID,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String?
) : Parcelable