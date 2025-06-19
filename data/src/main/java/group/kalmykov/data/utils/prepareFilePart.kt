package group.kalmykov.data.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun prepareFilePart(uri: Uri, context: Context): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)!!
    val fileBytes = inputStream.readBytes()
    inputStream.close()
    val requestBody = fileBytes.toRequestBody("image/*".toMediaTypeOrNull())
    val fileName = "upload.jpg" // Здесь ты можешь использовать имя из Uri, если хочешь
    return MultipartBody.Part.createFormData("file", fileName, requestBody)
}