package group.kalmykov.store.presentation.components.product_list

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import group.kalmykov.data.remote.dto.ProductDto
import group.kalmykov.shared.constants.ApiConstants
import group.kalmykov.store.R

@Composable
fun EditProductDialog(
    initialProduct: ProductDto,
    onUpdate: (ProductDto) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: ProductListViewModel = hiltViewModel()

    var product by rememberSaveable { mutableStateOf(initialProduct) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadError by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> imageUri = uri }

    LaunchedEffect(imageUri) {
        imageUri?.let {
            isUploading = true
            uploadError = null
            viewModel.uploadProductImage(it, context) { url, err ->
                if (url != null) product = product.copy(imageUrl = url)
                else uploadError = err
                isUploading = false
            }
        }
    }

    val isNameValid = product.name.isNotBlank()
    val isPriceValid = product.price > 0.0
    val formValid = isNameValid && isPriceValid && !isUploading

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.edit_product_dialog_title)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                val preview = imageUri ?: product.imageUrl?.let { ApiConstants.BASE_IMAGE_URL + it }
                preview?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = stringResource(R.string.image_preview_description),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 8.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                if (isUploading) {
                    LinearProgressIndicator(Modifier.fillMaxWidth())
                }
                uploadError?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
                Button(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.change_image_button))
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.name,
                    onValueChange = { product = product.copy(name = it) },
                    label = { Text(stringResource(R.string.name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isNameValid
                )
                if (!isNameValid) {
                    Text(
                        stringResource(R.string.enter_name_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                OutlinedTextField(
                    value = product.price.toString(),
                    onValueChange = {
                        it.toDoubleOrNull()?.let { p -> product = product.copy(price = p) }
                    },
                    label = { Text(stringResource(R.string.price_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isPriceValid
                )
                if (!isPriceValid) {
                    Text(
                        stringResource(R.string.enter_price_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                OutlinedTextField(
                    value = product.description.orEmpty(),
                    onValueChange = { product = product.copy(description = it) },
                    label = { Text(stringResource(R.string.description_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onUpdate(product) },
                enabled = formValid
            ) {
                Text(stringResource(R.string.save_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_button))
            }
        }
    )
}
