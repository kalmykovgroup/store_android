package group.kalmykov.store.presentation.components.product_list

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import group.kalmykov.data.remote.ApiService
import group.kalmykov.data.remote.dto.ProductDto
import group.kalmykov.data.utils.prepareFilePart
import java.util.UUID

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import group.kalmykov.store.R

@Composable
fun AddProductDialog(
    onAdd: (ProductDto) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: ProductListViewModel = hiltViewModel()

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadError by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> imageUri = uri }
    )

    LaunchedEffect(imageUri) {
        imageUri?.let {
            isUploading = true
            uploadError = null
            viewModel.uploadProductImage(it, context) { uploadedUrl, error ->
                if (uploadedUrl != null) imageUrl = uploadedUrl
                else uploadError = error
                isUploading = false
            }
        }
    }

    val isNameValid = name.isNotBlank()
    val priceValue = price.toDoubleOrNull()
    val isPriceValid = priceValue != null && priceValue > 0
    val isImageUploaded = imageUrl != null
    val isFormValid = isNameValid && isPriceValid && isImageUploaded && !isUploading

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_product_dialog_title)) },
        text = {
            Column {
                imageUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = stringResource(R.string.image_preview_description),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 8.dp)
                    )
                }
                if (isUploading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                uploadError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Button(
                    onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.choose_image_button))
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isNameValid
                )
                if (!isNameValid) {
                    Text(
                        text = stringResource(R.string.enter_name_error),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(stringResource(R.string.price_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isPriceValid
                )
                if (!isPriceValid && price.isNotBlank()) {
                    Text(
                        text = stringResource(R.string.enter_price_error),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val product = ProductDto(
                        id = UUID.randomUUID(),
                        name = name.trim(),
                        price = priceValue!!,
                        description = description.trim(),
                        imageUrl = imageUrl
                    )
                    Log.d("AddProductDialog", "ProductDto: $product")
                    onAdd(product)
                },
                enabled = isFormValid
            ) {
                Text(stringResource(R.string.add_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_button))
            }
        }
    )
}





